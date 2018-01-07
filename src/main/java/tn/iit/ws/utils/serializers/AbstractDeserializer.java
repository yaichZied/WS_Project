package tn.iit.ws.utils.serializers;

import java.io.IOException;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import tn.iit.ws.WsApplication;

public class AbstractDeserializer<T> extends StdDeserializer<T> {

	private static final long serialVersionUID = -2571876514376076895L;
	private EntityManager entityManager;
	
	public AbstractDeserializer(Class<?> vc) {
		super(vc);
		this.entityManager = WsApplication.appConext.getBean(EntityManager.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		Object id =node.get("id");
		Class<?> idType = UtilConstants.getIdFieldOfEntityClass(handledType()).getType();
		id=UtilConstants.parseValue(id, idType);
		T entity = (T) entityManager.find(handledType(), id);
		return entity;
	}
}
