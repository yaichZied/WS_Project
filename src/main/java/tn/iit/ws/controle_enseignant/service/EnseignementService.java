package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Enseignement;
import tn.iit.ws.controle_enseignant.repository.EnseignementRepository;

@Service
public class EnseignementService extends CrudService<Enseignement, Integer> {
	@Autowired
	private EnseignementRepository enseignementRepository;

	@Override
	protected JpaRepository<Enseignement, Integer> getRepository() {
		return enseignementRepository;
	}
}
