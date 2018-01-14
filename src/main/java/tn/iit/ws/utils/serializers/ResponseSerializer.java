package tn.iit.ws.utils.serializers;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import tn.iit.ws.security.config.authorization.BaseAuthorizationProvider;

public class ResponseSerializer extends StdSerializer<Object> {

	private static final long serialVersionUID = -7409356447075915597L;
	BaseAuthorizationProvider authorizationProvider;
	public ResponseSerializer(Class<Object> t,ApplicationContext appConext) {
		super(t);
		this.authorizationProvider=appConext.getBean(BaseAuthorizationProvider.class);
	}

	@Override
	public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		JavaType javaType = provider.constructType(value.getClass());
		BeanDescription beanDesc = provider.getConfig().introspect(javaType);
		JsonSerializer<Object> serializer = BeanSerializerFactory.instance.findBeanSerializer(provider, javaType,
				beanDesc);
		serializer.unwrappingSerializer(null).serialize(value, jgen, provider);
		Class<?> cl = value.getClass();
		String className = cl.getSimpleName();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean canEdit = authorizationProvider.hasPermission(auth, value, "EDIT");
		boolean canDelete = authorizationProvider.hasPermission(auth, value, "DELETE");
		jgen.writeStringField("className", String.format("%s%s",Character.toLowerCase(className.charAt(0)),className.substring(1)));
		jgen.writeBooleanField("canEdit", canEdit);
		jgen.writeBooleanField("canDelete", canDelete);
		jgen.writeEndObject();
	}

}