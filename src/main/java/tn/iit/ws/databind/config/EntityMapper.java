package tn.iit.ws.databind.config;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tn.iit.ws.utils.serializers.AbstractDeserializer;
import tn.iit.ws.utils.serializers.ClassSerializer;
import tn.iit.ws.utils.serializers.FieldSerializer;

@Configuration
public class EntityMapper {

	@Autowired
	private ApplicationContext appConetxt;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule entityModule = new SimpleModule();
		Reflections reflections = new Reflections("tn.iit.ws");
		Set<Class<? extends Object>> classes = reflections.getTypesAnnotatedWith(Entity.class);
		for (Iterator<Class<? extends Object>> iterator = classes.iterator(); iterator.hasNext();) {
			Class<? extends Object> clazz = iterator.next();
			AbstractDeserializer deser = new AbstractDeserializer(clazz, appConetxt, mapper);
			entityModule.addDeserializer(clazz, deser);
		}
		SimpleModule classFieldModule = new SimpleModule("ClassFieldModule", new Version(1, 0, 0, null, null, null));
		classFieldModule.addSerializer(Class.class, new ClassSerializer(Class.class));
		classFieldModule.addSerializer(Field.class, new FieldSerializer(Field.class));

		mapper.registerModule(entityModule);
		mapper.registerModule(classFieldModule);
		return mapper;
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(objectMapper());
		return jsonConverter;
	}
}