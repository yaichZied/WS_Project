package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.users.PointingAgent;
@Repository
public interface PointingAgentRepository extends JpaRepository<PointingAgent, Long> {
	
}
