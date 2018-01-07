package tn.iit.ws.databind.config;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tn.iit.ws.utils.serializers.AbstractDeserializer;

@Configuration
public class EntityMapper {

	@Autowired
	private ApplicationContext appConetxt;
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule entityModule = new SimpleModule();
        Reflections reflections = new Reflections("tn.iit.ws");
        System.out.println("adding ...");
        Set<Class<? extends Object>> classes = reflections.getTypesAnnotatedWith(Entity.class);
        System.out.println(classes);
        for (Iterator<Class<? extends Object>>  iterator = classes.iterator(); iterator.hasNext();) {
			Class<? extends Object> clazz = iterator.next();
			AbstractDeserializer deser = new AbstractDeserializer(clazz,appConetxt,mapper);
			System.out.println("adding "+clazz);
			entityModule.addDeserializer(clazz, deser);
		}
        mapper.registerModule(entityModule);
        return mapper;
    }
	
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	 MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	 jsonConverter.setObjectMapper(objectMapper());
	 return jsonConverter;
	}
}