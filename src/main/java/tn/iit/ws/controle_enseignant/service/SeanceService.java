package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Seance;
import tn.iit.ws.controle_enseignant.repository.SeanceRepository;

@Service
public class SeanceService extends CrudService<Seance, Integer> {
	@Autowired
	private SeanceRepository seanceRepository;

	@Override
	protected JpaRepository<Seance, Integer> getRepository() {
		return seanceRepository;
	}
}
