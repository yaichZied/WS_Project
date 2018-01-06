package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.persistence.ManyToOne;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tn.iit.ws.utils.search.criteria.Criteria;
import tn.iit.ws.utils.search.criteria.MultipleCriteria;
import tn.iit.ws.utils.search.criteria.SingleCriteria;

public class RequestDeserializer extends StdDeserializer<Criteria> {

	private static final long serialVersionUID = -2571876514376076895L;
	private final Class<?> ENTITY;

	public RequestDeserializer(Class<?> vc, Class<?> ENTITY) {
		super(vc);
		this.ENTITY = ENTITY;
	}

	@Override
	public Criteria deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		Criteria res = null;
		JsonNode node = jp.getCodec().readTree(jp);
		if (!node.isObject() || node.size() != 1) {
			throw new RuntimeException("Criteria must be an object with only 1 field");
		}
		String operator = node.fields().next().getKey().trim().toUpperCase();
		node = node.fields().next().getValue();
		if (!UtilConstants.isOperator(operator)) {
			throw new RuntimeException(String.format("%s is not a valid operator", operator));
		}
		if (UtilConstants.isSingleOperator(operator)&&node.isObject()) {
			SingleCriteria criteria = new SingleCriteria(ENTITY);
			operator = UtilConstants.getSingleOperator(operator);
			JsonNode fieldNode = node.get("field");
			JsonNode valueNode = node.get("value");
			if (fieldNode == null || !fieldNode.isTextual()) {
				throw new RuntimeException(String.format("field must be string"));
			}
			if (valueNode == null) {
				throw new RuntimeException(String.format("value must be string"));
			}
			String fieldName = fieldNode.asText().trim();
			Field field = criteria.searchField(fieldName);
			Class<?> fieldType = field.getType();
			if (field.isAnnotationPresent(ManyToOne.class)) {
				fieldType = UtilConstants.getIdFieldOfEntityClass(fieldType).getType();
			}
			Object value = UtilConstants.parseValue(valueNode, fieldType);
			criteria.setField(fieldName);
			criteria.setOperator(operator);
			criteria.setValue(value);
			return criteria;
		}
		if (UtilConstants.isMultipleOperator(operator)&&node.isArray()) {
			MultipleCriteria criteria = new MultipleCriteria();
			operator = UtilConstants.getMultipleOperator(operator);
			criteria.setOperator(operator);
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule("ResponseModule", new Version(1, 0, 0, null, null, null));
			module.addDeserializer(Criteria.class, new RequestDeserializer(Criteria.class, ENTITY));
			mapper.registerModule(module);
			criteria.setList(mapper.readValue(node.toString(), Criteria[].class));
			return criteria;
		}
		return res;
	}
}
