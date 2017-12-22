package tn.iit.ws.controller.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.InterruptionPeriod;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.time.InterruptionPeriodService;

@RestController
@RequestMapping("interruptionPeriod")
public class InterruptionPeriodController extends GenericController<InterruptionPeriod, Long> {
	@Autowired
	private InterruptionPeriodService interruptionPeriodService;

	@Override
	protected GenericService<InterruptionPeriod, Long> getService() {
		return interruptionPeriodService;
	}
}