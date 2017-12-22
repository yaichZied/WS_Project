package tn.iit.ws.controller.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.ContinuousTimeSlot;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.time.ContinuousTimeSlotService;

@RestController
@RequestMapping("continuousTimeSlot")
public class ContinuousTimeSlotController extends GenericController<ContinuousTimeSlot, Long> {
	@Autowired
	private ContinuousTimeSlotService continuousTimeSlotService;

	@Override
	protected GenericService<ContinuousTimeSlot, Long> getService() {
		return continuousTimeSlotService;
	}
}