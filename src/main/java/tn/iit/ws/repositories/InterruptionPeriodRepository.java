package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.time.InterruptionPeriod;
@Repository
public interface InterruptionPeriodRepository extends JpaRepository<InterruptionPeriod, Long> {
	
}
