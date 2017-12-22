package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.all.Floor;
@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
	
}
