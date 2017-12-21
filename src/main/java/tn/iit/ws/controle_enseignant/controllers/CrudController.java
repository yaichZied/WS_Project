package tn.iit.ws.controle_enseignant.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.iit.ws.controle_enseignant.exception.ExceptionCodes;
import tn.iit.ws.controle_enseignant.service.CrudService;

@Service
public abstract class CrudController<T,V> {

	protected abstract CrudService<T, V> getService();

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<T> listEntity() {
		return getService().findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public T getEntity(@PathVariable(name = "id") V id) {
		Optional<T> op = getService().findById(id);
		String className = getService().getClass().getSimpleName().replace("Service", "");
		if (op.isPresent())
			return getService().findById(id).get();
		throw new IllegalArgumentException(String.format("%s,%s,%s", ExceptionCodes.ID_NOT_EXISTANT,className,id));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	@ResponseBody
	public T updateEntity(@RequestBody T entity, @PathVariable(name = "id") V id) throws NoSuchFieldException,
			RuntimeException, NoSuchMethodException, IllegalAccessException, InstantiationException {
		if (getService().existsById(id))
		{
			getService().setIdOfEntity(entity, id);
			return getService().save(entity);
		}
		return getService().update(entity);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	public T addEntity(@RequestBody T entity) {
		if (getService().getIdOfEntity(entity)== null)
			return getService().save(entity);
		if (!getService().existsById(getService().getIdOfEntity(entity)))
		{
			return getService().save(entity);
		}
		throw new IllegalArgumentException(String.format("%s,%s,%s", ExceptionCodes.EXISTANT_ID,entity.getClass(),getService().getIdOfEntity(entity)));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteEntity(@PathVariable(name = "id") V id) {
		Optional<T> op = getService().findById(id);
		if (op.isPresent())
			getService().deleteById(id);
	}
}
