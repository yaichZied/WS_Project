package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Salle;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.SalleService;

@RestController
@RequestMapping("salle")
public class SalleController extends CrudController<Salle, Integer> {
	@Autowired
	private SalleService salleService;

	@Override
	protected CrudService<Salle, Integer> getService() {
		return salleService;
	}

}
