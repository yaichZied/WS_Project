package tn.iit.ws.controller.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.Semester;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.time.SemesterService;

@RestController
@RequestMapping("semester")
public class SemesterController extends GenericController<Semester, Long> {
	@Autowired
	private SemesterService semesterService;

	@Override
	protected GenericService<Semester, Long> getService() {
		return semesterService;
	}
}