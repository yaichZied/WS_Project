package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Seance;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.SeanceService;

@RestController
@RequestMapping("seance")
public class SeanceController extends CrudController<Seance, Integer> {
	@Autowired
	private SeanceService seanceService;

	@Override
	protected CrudService<Seance, Integer> getService() {
		return seanceService;
	}

}
