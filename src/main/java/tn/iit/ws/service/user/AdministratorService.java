package tn.iit.ws.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.users.Administrator;
import tn.iit.ws.repositories.AdminstratorRepository;
import tn.iit.ws.service.GenericService;

@Service
public class AdministratorService extends GenericService<Administrator, Long> {
	@Autowired
	private AdminstratorRepository administratorRepository;

	@Override
	protected JpaRepository<Administrator, Long> getRepository() {
		return administratorRepository;
	}

}
