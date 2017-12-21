package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Enseignant;
import tn.iit.ws.controle_enseignant.repository.EnseignantRepository;

@Service
public class EnseignantService extends CrudService<Enseignant, Integer> {
	@Autowired
	private EnseignantRepository enseignantRepository;

	@Override
	protected JpaRepository<Enseignant, Integer> getRepository() {
		return enseignantRepository;
	}
}
