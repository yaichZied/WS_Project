package tn.iit.ws.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.time.TimeSlot;
import tn.iit.ws.repositories.TimeSlotRepository;
import tn.iit.ws.service.GenericService;

@Service
public class TimeSlotService extends GenericService<TimeSlot, Long> {
	@Autowired
	private TimeSlotRepository timeSlotRepository;

	@Override
	protected JpaRepository<TimeSlot, Long> getRepository() {
		return timeSlotRepository;
	}

}
