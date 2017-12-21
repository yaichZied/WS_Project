package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Matiere;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.MatiereService;

@RestController
@RequestMapping("matiere")
public class MatiereController extends CrudController<Matiere, Integer> {
	@Autowired
	private MatiereService matiereService;

	@Override
	protected CrudService<Matiere, Integer> getService() {
		return matiereService;
	}

}
