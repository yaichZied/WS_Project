package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.Floor;
import tn.iit.ws.repositories.FloorRepository;
import tn.iit.ws.service.GenericService;

@Service
public class FloorService extends GenericService<Floor, Long> {
	@Autowired
	private FloorRepository floorRepository;

	@Override
	protected JpaRepository<Floor, Long> getRepository() {
		return floorRepository;
	}

}
