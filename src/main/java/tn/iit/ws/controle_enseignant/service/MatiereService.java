package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Matiere;
import tn.iit.ws.controle_enseignant.repository.MatiereRepository;

@Service
public class MatiereService extends CrudService<Matiere, Integer> {
	@Autowired
	private MatiereRepository matiereRepository;

	@Override
	protected JpaRepository<Matiere, Integer> getRepository() {
		return matiereRepository;
	}
}
