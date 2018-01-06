package tn.iit.ws.utils.serializers;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;

public class UtilConstants {
	private static final Pattern PATTERN_ENTITY = Pattern.compile("\\<(.*)\\,");
	private static final Map<String, String> REGEXES;
	static {
		REGEXES = new HashMap<>();
		REGEXES.put("email", "");
	}

	public static String getRegex(String name) {
		return REGEXES.get(name);
	}

	private static final Map<String, String> SINGLE_OPERATORS;
	static {
		SINGLE_OPERATORS = new HashMap<>();
		SINGLE_OPERATORS.put(">", ">");
		SINGLE_OPERATORS.put(">=", ">=");
		SINGLE_OPERATORS.put("<", "<");
		SINGLE_OPERATORS.put("<=", "<=");
		SINGLE_OPERATORS.put("=", "=");
		SINGLE_OPERATORS.put("==", "=");
		SINGLE_OPERATORS.put("===", "=");
		SINGLE_OPERATORS.put("!=", "!=");
		SINGLE_OPERATORS.put("!==", "!=");
		SINGLE_OPERATORS.put("<>", "!=");
		SINGLE_OPERATORS.put("LIKE", "LIKE");
	}

	public static boolean isSingleOperator(String operator) {
		return SINGLE_OPERATORS.containsKey(operator);
	}

	public static String getSingleOperator(String operator) {
		return SINGLE_OPERATORS.get(operator);
	}

	private static final Map<String, String> MULTIPLE_OPERATORS;
	static {
		MULTIPLE_OPERATORS = new HashMap<>();
		MULTIPLE_OPERATORS.put("&", "AND");
		MULTIPLE_OPERATORS.put("&&", "AND");
		MULTIPLE_OPERATORS.put("AND", "AND");
		MULTIPLE_OPERATORS.put("|", "OR");
		MULTIPLE_OPERATORS.put("||", "OR");
		MULTIPLE_OPERATORS.put("OR", "OR");
	}

	public static boolean isMultipleOperator(String operator) {
		return MULTIPLE_OPERATORS.containsKey(operator);
	}

	public static String getMultipleOperator(String operator) {
		return MULTIPLE_OPERATORS.get(operator);
	}

	public static boolean isOperator(String operator) {
		return isMultipleOperator(operator) || isSingleOperator(operator);
	}

	private static final List<Class<?>> NATIVES = Arrays.asList(
			new Class<?>[] { String.class, Integer.class, Long.class, Short.class, Double.class, Float.class, int.class,
					long.class, short.class, double.class, float.class, BigDecimal.class, BigInteger.class });

	public static boolean isNativeType(Class<?> clazz) {
		return NATIVES.contains(clazz);
	}

	private static final List<Class<?>> NUMERICS = Arrays
			.asList(new Class<?>[] { Integer.class, Long.class, Short.class, Double.class, Float.class, int.class,
					long.class, short.class, double.class, float.class, BigDecimal.class, BigInteger.class });

	public static boolean isNumericType(Class<?> clazz) {
		return NUMERICS.contains(clazz);
	}

	public static Object parseValue(Object obj, Class<?> clazz) {
		if (isNumericType(clazz))
			return parseNumericValue(obj, clazz);
		if (clazz.equals(String.class)) {
			if (obj instanceof JsonNode) {
				JsonNode node = (JsonNode) obj;
				return node.asText();
			}
			return obj.toString();
		}
		if (clazz.equals(Boolean.class)) {
			if (obj instanceof JsonNode) {
				JsonNode node = (JsonNode) obj;
				return node.asBoolean();
			}
			return obj.toString();
		}
		if (clazz.equals(Date.class)) {
			if (obj instanceof JsonNode) {
				JsonNode node = (JsonNode) obj;
				if(node.isDouble()) {
					return new Date((long)node.asDouble());
				}
				if(node.isLong()) {
					return new Date(node.asLong());
				}
				if(node.isTextual()) {
					obj = node.asText();
				}
				
			}
			if(obj instanceof String) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				try {
					return new Date(sdf.parse(obj.toString()).getTime());
				} catch (ParseException e) {
					try {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						return new Date(sdf.parse(obj.toString()).getTime());
					} catch (ParseException ex) {
						try {
							sdf = new SimpleDateFormat("yyyy/MM/dd");
							return new Date(sdf.parse(obj.toString()).getTime());
						} catch (ParseException ex1) {
							throw new NumberFormatException("Date can't be parsed \n"
									+ "provide a date as timestamp or in format yyyy/MM/dd , yyyy/MM/dd HH:mm or yyyy/MM/dd HH:mm:ss");
						}
					}
				}
			}
			throw new NumberFormatException();
		}
		if (clazz.equals(java.util.Date.class)) {
			if (obj instanceof JsonNode) {
				JsonNode node = (JsonNode) obj;
				if(node.isDouble()) {
					return new java.util.Date((long)node.asDouble());
				}
				if(node.isLong()) {
					return new java.util.Date(node.asLong());
				}
				if(node.isTextual()) {
					obj = node.asText();
				}
				
			}
			if(obj instanceof String) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				try {
					return sdf.parse(obj.toString());
				} catch (ParseException e) {
					try {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						return sdf.parse(obj.toString());
					} catch (ParseException ex) {
						try {
							sdf = new SimpleDateFormat("yyyy/MM/dd");
							return sdf.parse(obj.toString());
						} catch (ParseException ex1) {
							throw new NumberFormatException("Date can't be parsed \n"
									+ "provide a date as timestamp or in format yyyy/MM/dd , yyyy/MM/dd HH:mm or yyyy/MM/dd HH:mm:ss");
						}
					}
				}
			}
			throw new NumberFormatException();
		}
		throw new RuntimeException(String.format("Unresoved Type : %s", clazz.getSimpleName()));
	}

	private static Object parseNumericValue(Object obj, Class<?> clazz) {
		if (!isNumericType(clazz))
			throw new NumberFormatException();
		if (obj instanceof String) {
			return StringToNumeric(obj, clazz);
		}
		if (obj instanceof JsonNode) {
			JsonNode node = (JsonNode) obj;
			return StringToNumeric(node.asText(), clazz);
		}
		return StringToNumeric(obj.toString(), clazz);
	}

	private static Object StringToNumeric(Object obj, Class<?> clazz) {
		if (clazz.equals(Double.class) || clazz.equals(double.class)) {
			return Double.parseDouble(obj.toString());
		}
		if (clazz.equals(Float.class) || clazz.equals(float.class)) {
			return Float.parseFloat(obj.toString());
		}
		if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
			return Integer.parseInt(obj.toString());
		}
		if (clazz.equals(Long.class) || clazz.equals(long.class)) {
			return Long.parseLong(obj.toString());
		}
		if (clazz.equals(Short.class) || clazz.equals(short.class)) {
			return Short.parseShort(obj.toString());
		}
		if (clazz.equals(BigDecimal.class)) {
			return new BigDecimal(obj.toString());
		}
		if (clazz.equals(BigInteger.class)) {
			return new BigInteger(obj.toString());
		}
		return null;
	}

	public static Object getIdOfEntity(Object entity) {
		Field[] f = entity.getClass().getDeclaredFields();
		for (int i = 0; i < f.length; i++) {
			f[i].setAccessible(true);
			if (f[i].isAnnotationPresent(javax.persistence.Id.class)) {
				try {
					return f[i].get(entity);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Field getIdFieldOfEntityClass(Class<?> clazz) {
		Field[] f = clazz.getDeclaredFields();
		for (int i = 0; i < f.length; i++) {
			f[i].setAccessible(true);
			if (f[i].isAnnotationPresent(javax.persistence.Id.class)) {
				return f[i];
			}
		}
		return null;
	}

	public static void setIdOfEntity(Object entity, Object id) {
		Field[] f = entity.getClass().getDeclaredFields();
		for (int i = 0; i < f.length; i++) {
			f[i].setAccessible(true);
			if (f[i].isAnnotationPresent(javax.persistence.Id.class)) {
				try {
					f[i].set(entity, id);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Class<?> getEntityClass(Class<?> clazz) throws ClassNotFoundException {

		Class<?> cl = clazz;
		while (!cl.equals(Object.class)) {
			Matcher m = PATTERN_ENTITY.matcher(cl.getGenericSuperclass().getTypeName());
			if (m.find()) {
				return (Class<?>) Class.forName(m.group(1));
			}
		}
		return null;
	}
}
