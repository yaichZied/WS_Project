package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Enseignant;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.EnseignantService;

@RestController
@RequestMapping("enseignant")
public class EnseignantController extends CrudController<Enseignant, Integer> {
	@Autowired
	private EnseignantService enseignantService;

	@Override
	protected CrudService<Enseignant, Integer> getService() {
		return enseignantService;
	}

}
