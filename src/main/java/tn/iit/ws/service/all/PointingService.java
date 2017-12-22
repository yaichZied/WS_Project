package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.Pointing;
import tn.iit.ws.repositories.PointingRepository;
import tn.iit.ws.service.GenericService;

@Service
public class PointingService extends GenericService<Pointing, Long> {
	@Autowired
	private PointingRepository pointingRepository;

	@Override
	protected JpaRepository<Pointing, Long> getRepository() {
		return pointingRepository;
	}

}
