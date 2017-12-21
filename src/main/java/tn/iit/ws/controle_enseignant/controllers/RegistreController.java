package tn.iit.ws.controle_enseignant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controle_enseignant.entities.Registre;
import tn.iit.ws.controle_enseignant.service.CrudService;
import tn.iit.ws.controle_enseignant.service.RegistreService;

@RestController
@RequestMapping("registre")
public class RegistreController extends CrudController<Registre, String> {
	@Autowired
	private RegistreService registreService;

	@Override
	protected CrudService<Registre, String> getService() {
		return registreService;
	}

}
