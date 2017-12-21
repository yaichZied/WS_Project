package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Enseignement;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.EnseignementService;

@RestController
@RequestMapping("enseignement")
public class EnseignementController extends CrudController<Enseignement, Integer> {
	@Autowired
	private EnseignementService enseignementService;

	@Override
	protected CrudService<Enseignement, Integer> getService() {
		return enseignementService;
	}

}
