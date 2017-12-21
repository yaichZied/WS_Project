package tn.iit.ws.controle_enseignant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Datever;
import tn.iit.ws.controle_enseignant.repository.DateverRepository;

@Service
public class DateverService extends CrudService<Datever, String> {
	@Autowired
	private DateverRepository dateverRepository;

	@Override
	protected JpaRepository<Datever, String> getRepository() {
		return dateverRepository;
	}
}
