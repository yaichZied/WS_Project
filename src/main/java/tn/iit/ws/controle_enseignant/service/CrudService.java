package tn.iit.ws.controle_enseignant.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.ws.controle_enseignant.exception.ExceptionCodes;

public abstract class CrudService<T, V> {

	protected abstract JpaRepository<T, V> getRepository();

	public void deleteById(V id) {
		getRepository().deleteById(id);
	}

	public Optional<T> findById(V id) {
		return getRepository().findById(id);
	}

	public T save(T entity) {

		return getRepository().save(entity);
	}

	public T update(T entity) throws IllegalArgumentException, IllegalAccessException {
		Optional<T> op = getRepository().findById(getIdOfEntity(entity));
		T en = null;
		if (op.isPresent()) {
			en = op.get();

			Field[] f = entity.getClass().getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				f[i].setAccessible(true);
				if (f[i].get(entity) != null && Modifier.isStatic(f[i].getModifiers())
						&& !f[i].isAnnotationPresent(javax.persistence.Id.class)) {
					f[i].set(en, f[i].get(entity));
				}
			}
			return getRepository().save(en);
		}
		throw new IllegalArgumentException(
				String.format("%s,%s,%s", ExceptionCodes.ID_NOT_EXISTANT, entity.getClass(), getIdOfEntity(entity)));
	}

	public boolean existsById(V id) {
		return getRepository().existsById(id);
	}

	public List<T> findAll() {
		return getRepository().findAll();
	}

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
					f[i].set(entity,id);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
