package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import tn.iit.ws.security.config.authorization.BaseAuthorizationProvider;
@SuppressWarnings("rawtypes")
public class ClassSerializer extends StdSerializer<Class> {
	private static final long serialVersionUID = 6091816030284681907L;
	BaseAuthorizationProvider authorizationProvider;
	public ClassSerializer(Class<Class> t,ApplicationContext appConext) {
		super(t);
		this.authorizationProvider=appConext.getBean(BaseAuthorizationProvider.class);
	}

	@Override
	public void serialize(Class t, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
		Class<?> cl = t;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean canAdd = authorizationProvider.hasPermission(auth, t, "ADD");
		boolean canRead = authorizationProvider.hasPermission(auth, t, "CLASS_READ");
		Field[] fields;
		jsonGenerator.writeStartObject();
		if(canRead) {
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
				jsonGenerator.writeStartObject();
				jsonGenerator
						.writeStringField("name",String.format("%s%s", Character.toLowerCase(name.charAt(0)), name.substring(1)));
				
				boolean canAddSub = authorizationProvider.hasPermission(auth, clazz, "ADD");
				boolean canReadSub = authorizationProvider.hasPermission(auth, clazz, "CLASS_READ");
				jsonGenerator.writeBooleanField("canAdd", canAddSub);
				jsonGenerator.writeBooleanField("canRead", canReadSub);
				jsonGenerator.writeEndObject();
			}
			jsonGenerator.writeEndArray();
			if (Modifier.isAbstract(t.getModifiers())) {
				jsonGenerator.writeBooleanField("abstract", Boolean.TRUE);
			}
			jsonGenerator.writeBooleanField("canAdd", canAdd);
		}
		jsonGenerator.writeBooleanField("canRead", canRead);
		jsonGenerator.writeEndObject();
	}
}
