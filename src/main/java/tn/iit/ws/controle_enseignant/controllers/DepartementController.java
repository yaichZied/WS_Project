package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Departement;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.DepartementService;

@RestController
@RequestMapping("departement")
public class DepartementController extends CrudController<Departement, Integer> {
	@Autowired
	private DepartementService departementService;

	@Override
	protected CrudService<Departement, Integer> getService() {
		return departementService;
	}

}
