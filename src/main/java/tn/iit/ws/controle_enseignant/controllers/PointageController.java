package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Pointage;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.PointageService;

@RestController
@RequestMapping("pointage")
public class PointageController extends CrudController<Pointage, Integer> {
	@Autowired
	private PointageService pointageService;

	@Override
	protected CrudService<Pointage, Integer> getService() {
		return pointageService;
	}

}
