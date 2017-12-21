package tn.iit.ws.controle_enseignant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import tn.iit.ws.controle_enseignant.entities.Jour;
import tn.iit.ws.controle_enseignant.repository.JourRepository;

@Service
public class JourService extends CrudService<Jour, Integer> {
	@Autowired
	private JourRepository jourRepository;

	
	@PreAuthorize("hasAuthority('ROLE_TELLER')")
	@Override
	public Optional<Jour> findById(Integer id) {
		return super.findById(id);
	}
	@Override
	protected JpaRepository<Jour, Integer> getRepository() {
		return jourRepository;
	}
	
	@PreAuthorize("hasAuthority('ROLE_TELLER')")
	public Optional<Jour> findByIda(Integer id) {
		return super.findById(id);
	}
	
}
