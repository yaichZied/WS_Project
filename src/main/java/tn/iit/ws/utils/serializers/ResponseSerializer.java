package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ResponseSerializer<T> extends StdSerializer<T> {
	private static final long serialVersionUID = 6091816030284681907L;
	private String[] fields;
	private final Class<T> ENTITY;
	public ResponseSerializer(Class<T> t,Class<T> ENTITY) {
		super(t);
		this.ENTITY=ENTITY;
	}

	@Override
	public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
		jsonGenerator.writeStartObject();
		for (int i = 0; i < fields.length; i++) {
			try {
				Field field = ENTITY.getDeclaredField(fields[i]);
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				jsonGenerator.writeObjectField(field.getName(), field.get(t));
				field.setAccessible(accessible);
			} catch (Exception e) {
				e.printStackTrace();
			} 

		}
		Method method;
		try {
			method = ENTITY.getDeclaredMethod("getDisplayName");
			boolean accessible = method.isAccessible();
			method.setAccessible(true);
			Object obj = method.invoke(t);
			jsonGenerator.writeObjectField("displayName",obj );
			method.setAccessible(accessible);
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonGenerator.writeEndObject();
	}
	public String[] getFields() {
		return fields;
	}
	public void setFields(String[] fields) {
		this.fields = fields;
	}
}