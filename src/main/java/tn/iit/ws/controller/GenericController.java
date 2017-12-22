package tn.iit.ws.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.iit.ws.service.GenericService;
@CrossOrigin("*")
public abstract class GenericController<T, V extends Serializable> {
	protected abstract GenericService<T, V> getService();
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<T> findAll() {
		return getService().findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public T findById(@PathVariable(name = "id") V id) {
		return getService().findById(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseBody
	public T update(@RequestBody T entity, @PathVariable(name = "id") V id){
		return getService().update(entity);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	public T save(@RequestBody T entity) {
		return getService().save(entity);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteById(@PathVariable(name = "id") V id) {
		getService().deleteById(id);
	}
}
