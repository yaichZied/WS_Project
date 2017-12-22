package tn.iit.ws.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.time.ContinuousTimeSlot;
import tn.iit.ws.repositories.ContinuousTimeSlotRepository;
import tn.iit.ws.service.GenericService;

@Service
public class ContinuousTimeSlotService extends GenericService<ContinuousTimeSlot, Long> {
	@Autowired
	private ContinuousTimeSlotRepository continuousTimeSlotRepository;

	@Override
	protected JpaRepository<ContinuousTimeSlot, Long> getRepository() {
		return continuousTimeSlotRepository;
	}

}
