package tn.iit.ws.utils.search.criteria;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.persistence.ManyToOne;
import javax.persistence.Query;

import org.reflections.Reflections;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.exceptions.InexistantFieldException;
import tn.iit.ws.utils.serializers.UtilConstants;

@Getter
@Setter
public class SingleCriteria extends Criteria {
	private String field;
	private String operator;
	private Object value;
	private String id;
	private Class<?> clazz;
	public SingleCriteria(Class<?> clazz) {
		this.clazz=clazz;
	}
	@Override
	public String process(Class<?> clazz) {
		this.id = String.format("id%s", UUID.randomUUID().toString().replaceAll("\\-", "_"));
		return String.format(" %s %s %s ", field, operator, ":" + this.id);
	}

	@Override
	public void setValues(Query query) {
		System.out.println("setting value "+this.id+" : "+value);
		query.setParameter(this.id, value);
	}

	public void setField(String field) {
		String[] fields = field.split("\\.");
		this.field = buildField(fields);
	}

	private String buildField(String[] fields) {
		StringBuilder sb = new StringBuilder();
		buildField(fields, 0, clazz, sb);
		return sb.toString();
	}

	private static void buildField(String[] fields, int index, Class<?> clazz, StringBuilder sb) {
		Field field = findFieldInClass(clazz, fields[index]);
		if (field == null) {
			throw new InexistantFieldException(fields[index], clazz);
		}
		if (fields.length == index + 1) {
			sb.append(field.getName());
			if(field.isAnnotationPresent(ManyToOne.class)) {
				sb.append(String.format(".%s", UtilConstants.getIdFieldOfEntityClass(field.getType()).getName()));
			}
		} else {
			if (field.isAnnotationPresent(ManyToOne.class)) {
				sb.append(field.getName());
				sb.append(".");
				buildField(fields, index + 1, field.getDeclaringClass(), sb);
			} else {
				throw new RuntimeException(
						String.format("field %s of type %s is a native type", clazz.getSimpleName(), field.getName()));
			}
		}
	}

	public Field searchField(String field) {
		String[] fields = field.split("\\.");
		return searchField(fields, 0, clazz);
	}

	private Field searchField(String[] fields, int index, Class<?> clazz) {
		Field res = findFieldInClass(clazz, fields[index]);
		if (res == null) {
			throw new InexistantFieldException(fields[index], clazz);
		}
		if (fields.length == index + 1) {
			return res;
		} else {
			if (res.isAnnotationPresent(ManyToOne.class)) {
				return searchField(fields, index+1, res.getDeclaringClass());
			} else {
				throw new RuntimeException(
						String.format("field %s of type %s is a native type", clazz.getSimpleName(), res.getName()));
			}
		}
		
	}
	private static Field findFieldInClass(Class<?> clazz,String field)
	{
		return findFieldInClass(clazz, Object.class, field);
	}
	private static Field findFieldInClass(Class<?> clazz,Class<?> parent,String field)
	{
		Class<?> cl = clazz;
		Field res = null;
		while (!cl.equals(parent)) {
			try {
				return cl.getDeclaredField(field);
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			}
			cl=cl.getSuperclass();
		}
		Reflections reflections = new Reflections("tn.iit.ws");
		Set<?> classes = reflections.getSubTypesOf(clazz);
		Iterator<?> iter = classes.iterator();
		while (iter.hasNext()) {
			cl = (Class<?>) iter.next();
			res = findFieldInClass(cl, clazz, field);
			if(res!=null) {
				return res;
			}
		}
		return null;
	}
}
