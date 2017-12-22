package tn.iit.ws.controller.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.ExamsPeriod;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.time.ExamsPeriodService;

@RestController
@RequestMapping("examsPeriod")
public class ExamsPeriodController extends GenericController<ExamsPeriod, Long> {
	@Autowired
	private ExamsPeriodService examsPeriodService;

	@Override
	protected GenericService<ExamsPeriod, Long> getService() {
		return examsPeriodService;
	}
}