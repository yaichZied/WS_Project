package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.time.ContinuousTimeSlot;
@Repository
public interface ContinuousTimeSlotRepository extends JpaRepository<ContinuousTimeSlot, Long> {
	
}
