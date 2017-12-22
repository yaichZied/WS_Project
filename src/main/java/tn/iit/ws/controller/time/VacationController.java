package tn.iit.ws.controller.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.Vacation;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.time.VacationService;

@RestController
@RequestMapping("vaction")
public class VacationController extends GenericController<Vacation, Long> {
	@Autowired
	private VacationService vacationService;

	@Override
	protected GenericService<Vacation, Long> getService() {
		return vacationService;
	}
}