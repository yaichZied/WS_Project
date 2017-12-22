package tn.iit.ws.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.users.PointingAgent;
import tn.iit.ws.repositories.PointingAgentRepository;
import tn.iit.ws.service.GenericService;

@Service
public class PointingAgentService extends GenericService<PointingAgent, Long> {
	@Autowired
	private PointingAgentRepository pointingAgentRepository;

	@Override
	protected JpaRepository<PointingAgent, Long> getRepository() {
		return pointingAgentRepository;
	}

}
