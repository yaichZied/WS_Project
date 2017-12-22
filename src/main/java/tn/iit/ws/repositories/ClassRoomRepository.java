package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.all.ClassRoom;
@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
	
}
