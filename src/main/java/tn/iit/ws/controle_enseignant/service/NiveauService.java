package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Niveau;
import tn.iit.ws.controle_enseignant.repository.NiveauRepository;

@Service
public class NiveauService extends CrudService<Niveau, Integer> {
	@Autowired
	private NiveauRepository niveauRepository;

	@Override
	protected JpaRepository<Niveau, Integer> getRepository() {
		return niveauRepository;
	}
}
