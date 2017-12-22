package tn.iit.ws.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.ws.exceptions.DuplicateEntityException;
import tn.iit.ws.exceptions.NotFoundEntityException;

public abstract class GenericService<T, V extends Serializable> {
	protected abstract JpaRepository<T, V> getRepository();

	public void deleteById(V id) {
		if(!existsById(id))
			throw new NotFoundEntityException(id, Object.class);
		getRepository().delete(id);
	}

	public T findById(V id) {
		T entity = getRepository().findOne(id);
		if(entity!=null)
		{
			return entity;
		}
		throw new NotFoundEntityException(id,Object.class);
	}

	public T save(T entity) {
		if (getIdOfEntity(entity) == null)
			return getRepository().save(entity);
		if (!existsById(getIdOfEntity(entity))) {
			return getRepository().save(entity);
		}
		throw new DuplicateEntityException(getIdOfEntity(entity), entity.getClass());
	}

	public T update(T entity) {
		T oldEntity = getRepository().findOne(getIdOfEntity(entity));
		if (oldEntity != null) {
			Class<?> type = entity.getClass();
			while (type != null && type != Object.class) {
				Field[] f = type.getDeclaredFields();
				for (int i = 0; i < f.length; i++) {
					boolean accessible = f[i].isAccessible();
					f[i].setAccessible(true);
					try {
						if (f[i].get(entity) != null && Modifier.isStatic(f[i].getModifiers())
								&& !f[i].isAnnotationPresent(javax.persistence.Id.class)) {
							f[i].set(oldEntity, f[i].get(entity));
						}
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					}
					f[i].setAccessible(accessible);
				}
				type = type.getSuperclass();
			}
			return getRepository().save(oldEntity);
		}
		throw new NotFoundEntityException(entity.getClass(), entity.getClass());
	}
	public T update(T entity,V id) {
		setIdOfEntity(entity, id);
		return update(entity);
	}

	public boolean existsById(V id) {
		return existsById(id);
	}

	public List<T> findAll() {
		return getRepository().findAll();
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
}
