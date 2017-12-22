package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.time.Vacation;
@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {
	
}
