package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
@SuppressWarnings("rawtypes")
public class ClassSerializer extends StdSerializer<Class> {
	private static final long serialVersionUID = 6091816030284681907L;
	
	public ClassSerializer(Class<Class> t) {
		super(t);
	}

	@Override
	public void serialize(Class t, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
		Class<?> cl = t;
		Field[] fields;
		jsonGenerator.writeStartObject();
		List<Field> fList = new ArrayList<>();
		while (!cl.equals(Object.class)) {
			fields = cl.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if(!Modifier.isStatic(fields[i].getModifiers())){
					fList.add(fields[i]);
				}
			}
			
			cl = cl.getSuperclass();
		}

		jsonGenerator.writeObjectField("fields", fList);

		Reflections reflections = new Reflections("tn.iit.ws");
		@SuppressWarnings("unchecked")
		Set<?> classes = reflections.getSubTypesOf(t);
		Iterator<?> iter = classes.iterator();
		jsonGenerator.writeArrayFieldStart("subClasses");
		while (iter.hasNext()) {
			Class<?> clazz = (Class<?>) iter.next();
			String name = clazz.getSimpleName();
			jsonGenerator
					.writeString(String.format("%s%s", Character.toLowerCase(name.charAt(0)), name.substring(1)));
		}
		jsonGenerator.writeEndArray();
		if (Modifier.isAbstract(t.getModifiers())) {
			jsonGenerator.writeBooleanField("abstract", Boolean.TRUE);
		}
		jsonGenerator.writeEndObject();
	}
}
