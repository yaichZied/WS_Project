package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Salle;
import tn.iit.ws.controle_enseignant.repository.SalleRepository;

@Service
public class SalleService extends CrudService<Salle, Integer> {
	@Autowired
	private SalleRepository salleRepository;

	@Override
	protected JpaRepository<Salle, Integer> getRepository() {
		return salleRepository;
	}
}
