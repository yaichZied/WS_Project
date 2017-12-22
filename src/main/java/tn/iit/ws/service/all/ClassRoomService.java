package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.ClassRoom;
import tn.iit.ws.repositories.ClassRoomRepository;
import tn.iit.ws.service.GenericService;

@Service
public class ClassRoomService extends GenericService<ClassRoom, Long> {
	@Autowired
	private ClassRoomRepository classRoomRepository;

	@Override
	protected JpaRepository<ClassRoom, Long> getRepository() {
		return classRoomRepository;
	}

}
