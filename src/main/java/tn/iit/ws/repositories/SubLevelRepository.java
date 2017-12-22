package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.all.SubLevel;
@Repository
public interface SubLevelRepository extends JpaRepository<SubLevel, Long> {
	
}
