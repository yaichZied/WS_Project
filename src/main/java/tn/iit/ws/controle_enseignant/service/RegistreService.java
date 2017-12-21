package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Registre;
import tn.iit.ws.controle_enseignant.repository.RegistreRepository;

@Service
public class RegistreService extends CrudService<Registre, String> {
	@Autowired
	private RegistreRepository registreRepository;

	@Override
	protected JpaRepository<Registre, String> getRepository() {
		return registreRepository;
	}
}
