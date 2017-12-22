package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.all.Level;
@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
	
}
