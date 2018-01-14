package tn.iit.ws.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tn.iit.ws.entities.users.User;
import tn.iit.ws.utils.search.criteria.Criteria;
import tn.iit.ws.utils.serializers.CriteriaDeserializer;
import tn.iit.ws.utils.serializers.UtilConstants;

@CrossOrigin("*")
@SuppressWarnings("unchecked")
public abstract class GenericController<T, V extends Serializable> {

	public Class<T> ENTITY;
	@Autowired
	private EntityManager em;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public GenericController() {
		try {
			ENTITY = (Class<T>) UtilConstants.getEntityClass(getClass());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(this.ENTITY, 'CLASS_READ')")
	@PostFilter("hasPermission(filterObject, 'READ')")
	public List<?> findAll(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer skip,
			@RequestParam(required = false) Integer sort, @RequestParam(required = false) String orderBy) {
		return findImpl(offset, skip, orderBy, sort, null);
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(this.ENTITY, 'CLASS_READ')")
	@PostFilter("hasPermission(filterObject, 'READ')")
	public List<?> filter(HttpServletRequest request, @RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer skip, @RequestParam(required = false) Integer sort,
			@RequestParam(required = false) String orderBy) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("ResponseModule", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(Criteria.class, new CriteriaDeserializer(Criteria.class, ENTITY));
		mapper.registerModule(module);
		Criteria criteria=null;
		try {
			criteria = mapper.readValue(request.getInputStream(), Criteria.class);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return findImpl(offset, skip, orderBy, sort, criteria);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasPermission(this.ENTITY, 'CLASS_READ')")
	@PostAuthorize("hasPermission(returnObject, 'READ')")
	public T findById(@PathVariable(name = "id") V id) {
		return findByIdImpl(id);
	}

	@RequestMapping(value = "structure", method = RequestMethod.GET)
	@ResponseBody
	public Class<?> structure(HttpServletRequest request, HttpServletResponse response) {
		return ENTITY;
	}
	
	@RequestMapping(value = "default", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(this.ENTITY, 'ADD')")
	@ResponseBody
	public T defaultValue(HttpServletRequest request, HttpServletResponse response) {
		try {
			return ENTITY.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission(this.findByIdImpl(#id), 'EDIT')")
	@ResponseBody
	@Transactional
	public T update(@RequestBody T entity, @P("id") @PathVariable(name = "id") V id) {
		if (isAbstract()) {
			throw new UnsupportedOperationException("Can't update an abstract type");
		}
		T t = em.find(ENTITY, id);
		if (t != null) {
			Class<?> type = entity.getClass();
			while (type != null && type != Object.class) {
				Field[] f = type.getDeclaredFields();
				for (int i = 0; i < f.length; i++) {
					boolean accessible = f[i].isAccessible();
					f[i].setAccessible(true);
					try {
						Object value = f[i].get(entity);
						if (value != null && !Modifier.isStatic(f[i].getModifiers())
								&& !f[i].isAnnotationPresent(javax.persistence.Id.class)) {
							if(value instanceof Collection)
							{
								if(!((Collection<?>)value).isEmpty())
								{
									f[i].set(t, f[i].get(entity));
								}
							}
							else {
								f[i].set(t, f[i].get(entity));
								if (t instanceof User && f[i].getName().equals("password")) {
									User u = (User) t;
									u.setPassword(passwordEncoder.encode(u.getPassword()));
								}
							}
						}
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					}
					f[i].setAccessible(accessible);
				}
				type = type.getSuperclass();
			}
			System.out.println(t);
			em.persist(t);
		}
		return findByIdImpl(id);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	@PreAuthorize("hasPermission(this.ENTITY, 'ADD')")
	public T save(@RequestBody T t) {
		if (isAbstract()) {
			throw new UnsupportedOperationException("Can't update an abstract type");
		}
		UtilConstants.setIdOfEntity(t, null);
		if (t instanceof User) {
			User u = (User) t;
			u.setPassword(passwordEncoder.encode(u.getPassword()));
		}
		em.persist(t);
		return findByIdImpl((V) UtilConstants.getIdOfEntity(t));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional
	@PreAuthorize("hasPermission(this.findByIdImpl(#id), 'DELETE')")
	public String deleteById(@PathVariable(name = "id") V id) {
		try {
			em.remove(em.find(ENTITY, id));
			return "{\"removed\" : true}";
		} catch (Exception e) {
			return "{\"removed\" : false}";
		}
	}

	private String getOrderByFromRequest(String orderBy, Integer sort) {
		String res = orderBy;
		if (res == null || res.trim().isEmpty()) {
			return null;
		}
		Class<?> cl = ENTITY;
		while (!cl.equals(Object.class)) {
			try {
				cl.getDeclaredField(res);
				if (sort == null || sort == -1l) {
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

	private boolean isAbstract() {
		return Modifier.isAbstract(ENTITY.getModifiers());
	}

	private List<?> findImpl(Integer offset, Integer skip, String orderBy, Integer sort, Criteria criteria) {
		StringBuilder sb = new StringBuilder(String.format("from %s", ENTITY.getName()));
		orderBy=getOrderByFromRequest(orderBy, sort);
		if (orderBy != null) {
			sb.append(orderBy);
		}
		if (criteria != null) {
			sb.append(" WHERE ");
			sb.append(criteria.process(ENTITY));
		}
		Query query = em.createQuery(sb.toString());
		if (criteria != null) {
			criteria.setValues(query);
		}
		if (skip != null && skip > 0) {
			query.setFirstResult(skip);
		}
		if (offset != null && offset > 0) {
			query.setMaxResults(offset);
		}
		return query.getResultList();
	}

	public T findByIdImpl(V id) {
		return em.find(ENTITY, id);
	}
}
