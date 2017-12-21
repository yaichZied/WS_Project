package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Jour;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.JourService;

@RestController
@RequestMapping("jour")
public class JourController extends CrudController<Jour, Integer> {
	@Autowired
	private JourService jourService;

	@Override
	protected CrudService<Jour, Integer> getService() {
		return jourService;
	}
}
