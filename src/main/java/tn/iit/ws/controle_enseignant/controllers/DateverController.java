package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Datever;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.DateverService;

@RestController
@RequestMapping("datever")
public class DateverController extends CrudController<Datever, String> {
	@Autowired
	private DateverService dateverService;

	@Override
	protected CrudService<Datever, String> getService() {
		return dateverService;
	}

}
