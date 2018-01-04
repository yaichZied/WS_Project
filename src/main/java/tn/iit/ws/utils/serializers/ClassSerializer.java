package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ClassSerializer<T> extends StdSerializer<Class<T>> {
	private static final long serialVersionUID = 6091816030284681907L;
	private final Class<T> ENTITY;
	public ClassSerializer(Class<Class<T>> t,Class<T> ENTITY) {
		super(t);
		this.ENTITY=ENTITY;
	}

	@Override
	public void serialize(Class<T> t, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
		Class<?> cl = t;
		Field[] fields;
		jsonGenerator.writeStartObject();
		List<Field> fList = new ArrayList<>();
		while (!cl.equals(Object.class)) {
			fields = cl.getDeclaredFields();
			fList.addAll(Arrays.asList(fields));
			cl = cl.getSuperclass();
		}

		jsonGenerator.writeObjectField("fields", fList);

		Reflections reflections = new Reflections("tn.iit.ws");
		Set<Class<? extends T>> classes = reflections.getSubTypesOf(ENTITY);
		Iterator<Class<? extends T>> iter = classes.iterator();
		jsonGenerator.writeArrayFieldStart("subClasses");
		while (iter.hasNext()) {
			Class<? extends T> clazz = iter.next();
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
