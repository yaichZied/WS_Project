package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Subject;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.SubjectService;

@RestController
@RequestMapping("subject")
public class SubjectController extends GenericController<Subject, Long> {
	@Autowired
	private SubjectService subjectService;

	@Override
	protected GenericService<Subject, Long> getService() {
		return subjectService;
	}
}