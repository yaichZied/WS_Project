package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ResponseMapper<T> {
	private final ObjectMapper DEFAULT_MAPPER;
	private final ObjectMapper FIELDS_MAPPER;
	private final ResponseSerializer<T> FIELDS_SERIALIZER;

	@SuppressWarnings("unchecked")
	public ResponseMapper(Class<T> ENTITY) {

		SimpleModule responseModule = new SimpleModule("ResponseModule", new Version(1, 0, 0, null, null, null));

		FIELDS_SERIALIZER=new ResponseSerializer<T>(ENTITY, ENTITY);
		responseModule.addSerializer(ENTITY, FIELDS_SERIALIZER);

		SimpleModule classFieldModule = new SimpleModule("ClassFieldModule", new Version(1, 0, 0, null, null, null));
		classFieldModule.addSerializer((Class<Class<T>>) ENTITY.getClass(),
				new ClassSerializer<T>((Class<Class<T>>) ENTITY.getClass(), ENTITY));
		classFieldModule.addSerializer(Field.class, new FieldSerializer(Field.class));

		FIELDS_MAPPER = new ObjectMapper();
		FIELDS_MAPPER.registerModule(responseModule);
		FIELDS_MAPPER.registerModule(classFieldModule);

		DEFAULT_MAPPER = new ObjectMapper();
		DEFAULT_MAPPER.registerModule(classFieldModule);
	}

	public void sendResult(HttpServletRequest request, HttpServletResponse response, Object list)
			throws JsonGenerationException, JsonMappingException, IOException {
		String[] fields = getFieldsFromRequest(request);
		if (fields != null && fields.length>0) {
			FIELDS_SERIALIZER.setFields(fields);
			FIELDS_MAPPER.writeValue(response.getOutputStream(), list);
		} else {
			DEFAULT_MAPPER.writeValue(response.getOutputStream(), list);
		}
	}

	private String[] getFieldsFromRequest(HttpServletRequest request) {
		String fs = request.getParameter("fields");
		if (fs == null || fs.trim().isEmpty()) {
			return null;
		}
		String[] res = fs.split(",");
		for (int i = 0; i < res.length; i++) {
			if (res[i] == null || res[i].trim().isEmpty()) {
				return null;
			} else {
				res[i] = res[i].trim();
			}
		}
		return res;
	}
}
