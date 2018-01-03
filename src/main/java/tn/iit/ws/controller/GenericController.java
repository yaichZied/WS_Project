package tn.iit.ws.controller;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.utils.annotations.If;
import tn.iit.ws.utils.annotations.Max;
import tn.iit.ws.utils.annotations.Min;
import tn.iit.ws.utils.annotations.Regex;
import tn.iit.ws.utils.annotations.Type;

@CrossOrigin("*")
public abstract class GenericController<T, V extends Serializable> {
	private static final Pattern PATTERN_ENTITY = Pattern.compile("\\<(.*)\\,");
	private final Class<T> ENTITY;
	private static final List<Class<?>> NATIVES = Arrays.asList(
			new Class<?>[] { String.class, Integer.class, Long.class, Short.class, Double.class, Float.class, int.class,
					long.class, short.class, double.class, float.class, BigDecimal.class, BigInteger.class });
	private static final Map<String , String> REGEXES;
	static {
		REGEXES = new HashMap<>();
		REGEXES.put("email", "");
	}
	public GenericController() {
		try {
			ENTITY = getEntityClass();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Autowired
	EntityManager em;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void findAll(HttpServletRequest request, HttpServletResponse response) {
		Integer offset, skip;
		String orderBy;
		try {
			offset = Integer.parseInt(request.getParameter("offset"));
		} catch (Exception e) {
			offset = null;
		}
		try {
			skip = Integer.parseInt(request.getParameter("skip"));
		} catch (Exception e) {
			skip = 0;
		}
		orderBy = getOrderByFromRequest(request);

		StringBuilder sb = new StringBuilder(String.format("from %s", ENTITY.getName()));
		if (orderBy != null) {
			sb.append(orderBy);
		}
		Query query = em.createQuery(sb.toString());
		if (skip != null && skip > 0) {
			query.setFirstResult(skip);
		}
		if (offset != null && offset > 0) {
			query.setMaxResults(offset);
		}

		List<? extends T> list = query.getResultList();
		try {
			sendResult(request, response, list);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void filter(@RequestBody(required = false) CriteriaGroup criteria, HttpServletRequest request,
			HttpServletResponse response) {
		Integer offset, skip;
		String orderBy;
		try {
			offset = Integer.parseInt(request.getParameter("offset"));
		} catch (Exception e) {
			offset = null;
		}
		try {
			skip = Integer.parseInt(request.getParameter("skip"));
		} catch (Exception e) {
			skip = 0;
		}
		orderBy = getOrderByFromRequest(request);

		StringBuilder sb = new StringBuilder(String.format("from %s", ENTITY.getName()));

		if (criteria != null) {
			sb.append(" WHERE ");
			sb.append(criteria.process(ENTITY));
		}
		if (orderBy != null) {
			sb.append(orderBy);
		}
		Query query = em.createQuery(sb.toString());
		criteria.setValues(query);
		if (skip != null) {
			query.setFirstResult(skip);
		}
		if (offset != null) {
			query.setMaxResults(offset);
		}

		List<? extends T> list = query.getResultList();
		try {
			sendResult(request, response, list);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public void findById(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "id") V id) {
		T t = em.find(ENTITY, id);
		try {
			sendResult(request, response, t);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "structure", method = RequestMethod.GET)
	@ResponseBody
	public void structure(HttpServletRequest request, HttpServletResponse response) {
		try {
			sendResult(request, response, ENTITY);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public void update(HttpServletRequest request, HttpServletResponse response, @RequestBody T entity,
			@PathVariable(name = "id") V id) {
		T t = em.find(ENTITY, id);
		if (t != null) {
			Class<?> type = entity.getClass();
			while (type != null && type != Object.class) {
				Field[] f = type.getDeclaredFields();
				for (int i = 0; i < f.length; i++) {
					boolean accessible = f[i].isAccessible();
					f[i].setAccessible(true);
					try {
						if (f[i].get(entity) != null && !Modifier.isStatic(f[i].getModifiers())
								&& !f[i].isAnnotationPresent(javax.persistence.Id.class)) {
							f[i].set(t, f[i].get(entity));
						}
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					}
					f[i].setAccessible(accessible);
				}
				type = type.getSuperclass();
			}
			em.persist(t);
			em.flush();
			em.refresh(t);
			try {
				sendResult(request, response, t);
			} catch (JsonGenerationException e) {
			} catch (JsonMappingException e) {
			} catch (IOException e) {
			}
			return;
		}

	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody T t) {
		setIdOfEntity(t, null);
		em.persist(t);
		em.flush();
		em.refresh(t);
		try {
			sendResult(request, response, t);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional
	public String deleteById(@PathVariable(name = "id") V id) {
		em.remove(em.find(ENTITY, id));
		return "{\"success\" : true}";
	}

	private void sendResult(HttpServletRequest request, HttpServletResponse response, Object list)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String[] fields = getFieldsFromRequest(request);
		if (fields != null) {
			SimpleModule module = new SimpleModule("CustomEntitySerializer", new Version(1, 0, 0, null, null, null));
			module.addSerializer(ENTITY, new CustomSerializer(ENTITY, fields));
			mapper.registerModule(module);
		}

		SimpleModule module = new SimpleModule("ClassSerializer", new Version(1, 0, 0, null, null, null));
		module.addSerializer(Class.class, new ClassSerializer(Class.class));
		module.addSerializer(Field.class, new FieldSerializer(Field.class));
		// module.addSerializer(Object.class, new
		// ObjectSerializer(Object.class));
		mapper.registerModule(module);
		mapper.writeValue(response.getOutputStream(), list);
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

	private String getOrderByFromRequest(HttpServletRequest request) {
		String res = request.getParameter("orderBy");
		if (res == null || res.trim().isEmpty()) {
			return null;
		}
		Class<?> cl = ENTITY;
		while (!cl.equals(Object.class)) {
			try {
				cl.getDeclaredField(res);
				String sort = request.getParameter("sort");
				if (sort == null || sort.trim().isEmpty() || sort.trim().equals("-1")) {
					return String.format(" ORDER BY %s asc ", res);
				} else {
					return String.format(" ORDER BY %s desc ", res);
				}
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			}
		}
		return null;
	}

	private String getStringClass() {
		Class<?> cl = getClass();
		do {
			if (cl.getSuperclass().equals(GenericController.class)) {
				return cl.getGenericSuperclass().getTypeName();
			}
			cl = cl.getSuperclass();
		} while (cl != Object.class);
		return "";
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() throws ClassNotFoundException {
		Matcher m = PATTERN_ENTITY.matcher(getStringClass());
		m.find();
		return (Class<T>) Class.forName(m.group(1));
	}

	private class CustomSerializer extends StdSerializer<T> {
		private static final long serialVersionUID = 6091816030284681907L;
		private String[] fields;

		public CustomSerializer(Class<T> t, String[] fields) {
			super(t);
			this.fields = fields;
		}

		@Override
		public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
			jsonGenerator.writeStartObject();
			for (int i = 0; i < fields.length; i++) {
				try {
					Field field = ENTITY.getDeclaredField(fields[i]);
					boolean accessible = field.isAccessible();
					field.setAccessible(true);
					jsonGenerator.writeObjectField(field.getName(), field.get(t));
					field.setAccessible(accessible);
				} catch (NoSuchFieldException e) {
				} catch (SecurityException e) {
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}

			}
			Method method;
			try {
				method = ENTITY.getDeclaredMethod("getDisplayName");
				boolean accessible = method.isAccessible();
				method.setAccessible(true);
				jsonGenerator.writeObjectField("displayName", method.invoke(t));
				method.setAccessible(accessible);
				jsonGenerator.writeEndObject();
			} catch (NoSuchMethodException | SecurityException e) {
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	public V getIdOfEntity(T entity) {
		Field[] f = entity.getClass().getDeclaredFields();
		for (int i = 0; i < f.length; i++) {
			f[i].setAccessible(true);
			if (f[i].isAnnotationPresent(javax.persistence.Id.class)) {
				try {
					return (V) f[i].get(entity);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
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

	public void setIdOfEntity(T entity, V id) {
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

	private @Getter @Setter static class Criteria {
		private String field;
		private String operator;
		private String value;
		private String id;

		public String process(Class<?> clazz) {
			String verif = verify(clazz);
			if (verif != null) {
				this.id = "id" + UUID.randomUUID().toString().replaceAll("\\-", "_");
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
						if (NATIVES.contains(f.getType())) {
							return String.format("%s.%s", res, f.getName());
						} else {
							String id = getEntityIdFieldName(f.getType());
							return String.format("%s.%s.%s", res, f.getName(), id);
						}
					} else {

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
	}

	@Getter
	@Setter
	private static class CriteriaGroup {
		private String name;
		private CriteriaGroup[] groups;
		private Criteria[] filters;

		public String process(Class<?> clazz) {
			if (verify()) {
				StringBuilder sb = new StringBuilder(" ( ");
				boolean begin = false;
				if (filters != null && filters.length > 0) {
					begin = true;
					for (int i = 0; i < filters.length; i++) {
						sb.append(filters[i].process(clazz));
						if (i < filters.length - 1 || (groups != null && groups.length > 0)) {

							sb.append(" ");
							sb.append(name);
						}
						sb.append(" ");
					}
				}
				if (groups != null && groups.length > 0) {
					begin = true;
					for (int i = 0; i < groups.length; i++) {
						sb.append(groups[i].process(clazz));
						if (i < groups.length - 1) {

							sb.append(" ");
							sb.append(name);
						}
						sb.append(" ");
					}
				}
				sb.append(" ) ");
				if (begin) {
					return sb.toString();
				}

			}
			return " (true) ";
		}

		public void setValues(Query query) {
			if (filters != null && filters.length > 0) {
				for (int i = 0; i < filters.length; i++) {
					filters[i].setValues(query);
				}
			}
			if (groups != null && groups.length > 0) {
				for (int i = 0; i < groups.length; i++) {
					groups[i].setValues(query);
				}
			}
		}

		private boolean verify() {
			return filters != null && filters.length > 0
					&& (name.equalsIgnoreCase("and") || name.equalsIgnoreCase("or"));
		}

	}

	private class ClassSerializer extends StdSerializer<Class> {
		private static final long serialVersionUID = 6091816030284681907L;

		public ClassSerializer(Class t) {
			super(t);
		}

		@Override
		public void serialize(Class t, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
			Class cl = t;
			Field[] fields;
			jsonGenerator.writeStartObject();
			List<Field> fList = new ArrayList<>();
			while (!cl.equals(Object.class)) {
				fields = cl.getDeclaredFields();
				fList.addAll(Arrays.asList(fields));
				cl = cl.getSuperclass();
			}

			jsonGenerator.writeObjectField("fields", fList);

			Reflections reflections = new Reflections("tn.iit.ws.entities");
			Set<Class<? extends T>> classes = reflections.getSubTypesOf(ENTITY);
			Iterator<Class<? extends T>> iter = classes.iterator();
			jsonGenerator.writeArrayFieldStart("subClasses");
			while (iter.hasNext()) {
				String name = iter.next().getSimpleName();

				jsonGenerator
						.writeString(String.format("%s%s", Character.toLowerCase(name.charAt(0)), name.substring(1)));
			}
			jsonGenerator.writeEndArray();
			if (Modifier.isAbstract(t.getModifiers())) {
				jsonGenerator.writeBooleanField("abstract", Boolean.TRUE);
			}
			jsonGenerator.writeEndObject();
		}
	}

	private class FieldSerializer extends StdSerializer<Field> {
		private static final long serialVersionUID = 6091816030284681907L;

		public FieldSerializer(Class t) {
			super(t);
		}

		@Override
		public void serialize(Field t, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStringField("name", t.getName());
			String s = t.getType().getSimpleName();
			s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
			Type type = t.getAnnotation(Type.class);
			if(type!=null&&!type.value().isEmpty()) {
				s = type.value();
			}
			Max max = t.getAnnotation(Max.class);
			if(max!=null&&!max.value().isEmpty()) {
				jsonGenerator.writeStringField("max", max.value());
			}

			Min min = t.getAnnotation(Min.class);
			if(min!=null&&!min.value().isEmpty()) {
				jsonGenerator.writeStringField("min", min.value());
			}
			Regex regex = t.getAnnotation(Regex.class);
			if(regex!=null) {
				if(!regex.type().isEmpty()&&REGEXES.get(regex.type())!=null) {
					jsonGenerator.writeStringField("regex", REGEXES.get(regex.type()));
				}
				else
				{
					if(!regex.value().isEmpty()) {
						jsonGenerator.writeStringField("regex", regex.value());
					}
				}
			}
			If iff = t.getAnnotation(If.class);
			if(iff!=null&&!iff.value().isEmpty()) {
				StringBuilder sb = new StringBuilder();
				if(!iff.not()) {
					sb.append("!");
				}
				sb.append("entity");
				String[] list = iff.value().split("\\.");
				for (int i = 0; i < list.length; i++) {
					sb.append(String.format("['%s']", list[i].trim()));
				}
				if(!iff.operator().isEmpty()) {
					sb.append("  ");
					sb.append(iff.operator());
					sb.append("  ");
					sb.append(iff.operand());
				}
				
				jsonGenerator.writeStringField("if", sb.toString());
			}
			jsonGenerator.writeStringField("type", s);
			if (t.isAnnotationPresent(Id.class)) {
				jsonGenerator.writeBooleanField("id", Boolean.TRUE);
			}
			if (t.isAnnotationPresent(ManyToMany.class)) {
				jsonGenerator.writeBooleanField("manyToMany", Boolean.TRUE);
			}
			if (t.isAnnotationPresent(ManyToOne.class)) {
				jsonGenerator.writeBooleanField("manyToOne", Boolean.TRUE);
			}
			if (t.isAnnotationPresent(OneToMany.class)) {
				jsonGenerator.writeBooleanField("oneToMany", Boolean.TRUE);
			}
			if (t.isAnnotationPresent(OneToOne.class)) {
				jsonGenerator.writeBooleanField("oneToOne", Boolean.TRUE);
			}
			jsonGenerator.writeEndObject();
		}
	}
}
