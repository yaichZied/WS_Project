package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Niveau;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.NiveauService;

@RestController
@RequestMapping("niveau")
public class NiveauController extends CrudController<Niveau, Integer> {
	@Autowired
	private NiveauService niveauService;

	@Override
	protected CrudService<Niveau, Integer> getService() {
		return niveauService;
	}

}
