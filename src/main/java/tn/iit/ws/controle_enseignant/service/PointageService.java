package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Pointage;
import tn.iit.ws.controle_enseignant.repository.PointageRepository;

@Service
public class PointageService extends CrudService<Pointage, Integer> {
	@Autowired
	private PointageRepository pointageRepository;

	@Override
	protected JpaRepository<Pointage, Integer> getRepository() {
		return pointageRepository;
	}
}
