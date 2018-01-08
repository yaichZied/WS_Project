package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class AbstractDeserializer<T> extends StdDeserializer<T> {

	private static final long serialVersionUID = -2571876514376076895L;
	private EntityManager entityManager;
	private ObjectMapper mapper;

	public AbstractDeserializer(Class<?> vc, ApplicationContext appContext, ObjectMapper mapper) {
		super(vc);
		this.entityManager = appContext.getBean(EntityManager.class);
		this.mapper = mapper;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		Iterator<Entry<String, JsonNode>> i = node.fields();
		Field idField = UtilConstants.getIdFieldOfEntityClass(handledType());
		JsonNode idNode = node.get(idField.getName());
		T entity = null;
		if (idNode!=null&&idNode.isValueNode()) {
			String textId = idNode.asText();
			if (textId != null && !textId.isEmpty()) {
				Class<?> idType = UtilConstants.getIdFieldOfEntityClass(handledType()).getType();
				Object id = UtilConstants.parseValue(textId, idType);
				entity = (T) entityManager.find(handledType(), id);
			}
		}
		if (entity == null) {
			Class<?> orginType = handledType();
			if (!Modifier.isAbstract(orginType.getModifiers())) {
				try {
					entity = (T) orginType.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (entity != null) {
			while (i.hasNext()) {
				Entry<String, JsonNode> entry = (Entry<String, JsonNode>) i.next();
				if (!entry.getKey().equals(idField.getName()) || (UtilConstants.getIdOfEntity(entity) == null) ) {
					Field classField = UtilConstants.getFieldOfEntityClass(entity.getClass(), entry.getKey());
					if (classField != null) {
						boolean accessible = classField.isAccessible();
						classField.setAccessible(true);
						try {
							try {
								classField.set(entity,
										UtilConstants.parseValue(entry.getValue(), classField.getType()));
							} catch (Exception e) {
								try {
									classField.set(entity,
											mapper.readValue(entry.getValue().toString(), classField.getType()));
								} catch (Exception e2) {
									e.printStackTrace();
									e2.printStackTrace();
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
						classField.setAccessible(accessible);
					}
				}
			}
		}
		return entity;
	}
}
