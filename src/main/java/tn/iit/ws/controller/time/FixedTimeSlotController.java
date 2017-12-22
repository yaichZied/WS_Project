package tn.iit.ws.controller.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.FixedTimeSlot;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.time.FixedTimeSlotService;

@RestController
@RequestMapping("fixedTimeSlot")
public class FixedTimeSlotController extends GenericController<FixedTimeSlot, Long> {
	@Autowired
	private FixedTimeSlotService fixedTimeSlotService;

	@Override
	protected GenericService<FixedTimeSlot, Long> getService() {
		return fixedTimeSlotService;
	}
}