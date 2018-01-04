package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import tn.iit.ws.utils.annotations.If;
import tn.iit.ws.utils.annotations.Max;
import tn.iit.ws.utils.annotations.Min;
import tn.iit.ws.utils.annotations.Regex;
import tn.iit.ws.utils.annotations.Type;

public class FieldSerializer extends StdSerializer<Field> {
	private static final long serialVersionUID = 6091816030284681907L;
	
	public FieldSerializer(Class<Field> t) {
		super(t);
	}

	@Override
	public void serialize(Field t, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("name", t.getName());
		String s = t.getType().getSimpleName();
		s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
		Type type = t.getAnnotation(Type.class);
		if(type!=null&&!type.value().isEmpty()) {
			s = type.value();
		}
		Max max = t.getAnnotation(Max.class);
		if(max!=null&&!max.value().isEmpty()) {
			jsonGenerator.writeStringField("max", max.value());
		}

		Min min = t.getAnnotation(Min.class);
		if(min!=null&&!min.value().isEmpty()) {
			jsonGenerator.writeStringField("min", min.value());
		}
		Regex regex = t.getAnnotation(Regex.class);
		if(regex!=null) {
			if(!regex.type().isEmpty()&&UtilConstants.getRegex(regex.type())!=null) {
				jsonGenerator.writeStringField("regex", UtilConstants.getRegex(regex.type()));
			}
			else
			{
				if(!regex.value().isEmpty()) {
					jsonGenerator.writeStringField("regex", regex.value());
				}
			}
		}
		If iff = t.getAnnotation(If.class);
		if(iff!=null&&!iff.value().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			if(iff.not()) {
				sb.append("!");
			}
			sb.append("entity");
			String[] list = iff.value().split("\\.");
			for (int i = 0; i < list.length; i++) {
				sb.append(String.format("['%s']", list[i].trim()));
			}
			if(!iff.operator().isEmpty()) {
				sb.append("  ");
				sb.append(iff.operator());
				sb.append("  ");
				sb.append(iff.operand());
			}
			
			jsonGenerator.writeStringField("if", sb.toString());
		}
		jsonGenerator.writeStringField("type", s);
		if (t.isAnnotationPresent(Id.class)) {
			jsonGenerator.writeBooleanField("id", Boolean.TRUE);
		}
		if (t.isAnnotationPresent(ManyToMany.class)) {
			jsonGenerator.writeBooleanField("manyToMany", Boolean.TRUE);
		}
		if (t.isAnnotationPresent(ManyToOne.class)) {
			jsonGenerator.writeBooleanField("manyToOne", Boolean.TRUE);
		}
		if (t.isAnnotationPresent(OneToMany.class)) {
			jsonGenerator.writeBooleanField("oneToMany", Boolean.TRUE);
		}
		if (t.isAnnotationPresent(OneToOne.class)) {
			jsonGenerator.writeBooleanField("oneToOne", Boolean.TRUE);
		}
		jsonGenerator.writeEndObject();
	}
}
