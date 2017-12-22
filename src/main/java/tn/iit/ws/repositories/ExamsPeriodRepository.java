package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.time.ExamsPeriod;
@Repository
public interface ExamsPeriodRepository extends JpaRepository<ExamsPeriod, Long> {
	
}
