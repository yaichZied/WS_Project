package tn.iit.ws.controller.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.TimeSlot;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.time.TimeSlotService;

@RestController
@RequestMapping("timeSlot")
public class TimeSlotController extends GenericController<TimeSlot, Long> {
	@Autowired
	private TimeSlotService timeSlotService;

	@Override
	protected GenericService<TimeSlot, Long> getService() {
		return timeSlotService;
	}
}