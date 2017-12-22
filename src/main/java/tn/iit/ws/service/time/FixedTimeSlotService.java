package tn.iit.ws.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.time.FixedTimeSlot;
import tn.iit.ws.repositories.FixedTimeSlotRepository;
import tn.iit.ws.service.GenericService;

@Service
public class FixedTimeSlotService extends GenericService<FixedTimeSlot, Long> {
	@Autowired
	private FixedTimeSlotRepository fixedTimeSlotRepository;

	@Override
	protected JpaRepository<FixedTimeSlot, Long> getRepository() {
		return fixedTimeSlotRepository;
	}

}
