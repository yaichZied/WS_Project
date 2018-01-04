package tn.iit.ws.utils.search;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.utils.serializers.UtilConstants;

public @Getter @Setter class Criteria {
	private String field;
	private String operator;
	private String value;
	private String id;

	public String process(Class<?> clazz) {
		String verif = verify(clazz);
		if (verif != null) {
			this.id = String.format("id%s","id" + UUID.randomUUID().toString().replaceAll("\\-", "_"));
			return String.format(" %s %s %s ", verif, operator, ":" + this.id);
		} else {
			return "true";
		}

	}

	private String verify(Class<?> clazz) {
		boolean existField = false;
		Class<?> cl = clazz;
		while (!cl.equals(Object.class)) {
			try {
				cl.getDeclaredField(field);
				existField = true;
				break;
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			}
		}
		if (!existField) {
			return null;
		}
		if (!operators.contains(operator)) {
			return null;
		}
		return existField(clazz);
	}

	List<String> operators = Arrays.asList(new String[] { ">", "<", ">=", "<=", "=" });

	private String existField(String[] fields, Class<?> clazz, int index, String res) {
		Class<?> cl = clazz;
		while (!clazz.equals(Object.class)) {
			try {
				Field f = cl.getDeclaredField(fields[index]);
				if (index + 1 == fields.length) {
					if (UtilConstants.isNativeType(f.getType())) {
						return String.format("%s.%s", res, f.getName());
					} else {
						String id = getEntityIdFieldName(f.getType());
						return String.format("%s.%s.%s", res, f.getName(), id);
					}
				}
				return existField(fields, f.getType(), index + 1, String.format("%s.%s", res, f.getName()));
			} catch (NoSuchFieldException e) {
				return null;
			} catch (SecurityException e) {
				return null;
			}
		}
		return null;
	}

	private String existField(Class<?> clazz) {

		return existField(field.split("\\."), clazz, 0, "").substring(1);
	}

	public void setValues(Query query) {
		query.setParameter(this.id, value);

	}
	public static String getEntityIdFieldName(Class<?> clazz) {
		Class<?> cl = clazz;
		while (!cl.equals(Object.class)) {
			Field[] f = cl.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				f[i].setAccessible(true);
				if (f[i].isAnnotationPresent(javax.persistence.Id.class)) {
					return f[i].getName();
				}
			}
			cl = cl.getSuperclass();
		}
		return null;
	}
}