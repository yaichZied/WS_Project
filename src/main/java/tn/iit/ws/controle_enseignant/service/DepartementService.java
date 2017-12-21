package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Departement;
import tn.iit.ws.controle_enseignant.repository.DepartementRepository;

@Service
public class DepartementService extends CrudService<Departement, Integer> {
	@Autowired
	private DepartementRepository departementRepository;

	@Override
	protected JpaRepository<Departement, Integer> getRepository() {
		return departementRepository;
	}
}
