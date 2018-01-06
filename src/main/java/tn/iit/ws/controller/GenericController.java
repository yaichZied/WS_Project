package tn.iit.ws.controller;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tn.iit.ws.utils.search.criteria.Criteria;
import tn.iit.ws.utils.serializers.CriteriaDeserializer;
import tn.iit.ws.utils.serializers.ResponseMapper;
import tn.iit.ws.utils.serializers.UtilConstants;

@CrossOrigin("*")
public abstract class GenericController<T, V extends Serializable> {

	private final Class<T> ENTITY;
	@Autowired
	private EntityManager em;

	private ResponseMapper<T> responseMapper;

	@SuppressWarnings("unchecked")
	public GenericController() {
		try {
			ENTITY = (Class<T>) UtilConstants.getEntityClass(getClass());
			this.responseMapper = new ResponseMapper<>(ENTITY);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@ResponseBody
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

		List<?> list = query.getResultList();
		try {
			responseMapper.sendResult(request, response, list);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void filter(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("ResponseModule", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(Criteria.class, new CriteriaDeserializer(Criteria.class, ENTITY));
		mapper.registerModule(module);
		Criteria criteria=null;
		try {
			criteria = mapper.readValue(request.getInputStream(), Criteria.class);
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		System.out.println(query);
		
		System.out.println(sb.toString());;
		if (skip != null) {
			query.setFirstResult(skip);
		}
		if (offset != null) {
			query.setMaxResults(offset);
		}

		List<?> list = query.getResultList();
		try {
			responseMapper.sendResult(request, response, list);
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
			responseMapper.sendResult(request, response, t);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "structure", method = RequestMethod.GET)
	@ResponseBody
	public void structure(HttpServletRequest request, HttpServletResponse response) {
		try {
			responseMapper.sendResult(request, response, ENTITY);
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
				responseMapper.sendResult(request, response, t);
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
		UtilConstants.setIdOfEntity(t, null);
		em.persist(t);
		em.flush();
		em.refresh(t);
		try {
			responseMapper.sendResult(request, response, t);
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
}
