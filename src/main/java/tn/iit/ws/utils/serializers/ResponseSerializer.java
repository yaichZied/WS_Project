package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}

		}
		Method method;
		try {
			method = ENTITY.getDeclaredMethod("getDisplayName");
			boolean accessible = method.isAccessible();
			method.setAccessible(true);
			jsonGenerator.writeObjectField("displayName", method.invoke(t));
			method.setAccessible(accessible);
			jsonGenerator.writeEndObject();
		} catch (NoSuchMethodException | SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		
	}
	public String[] getFields() {
		return fields;
	}
	public void setFields(String[] fields) {
		this.fields = fields;
	}
}