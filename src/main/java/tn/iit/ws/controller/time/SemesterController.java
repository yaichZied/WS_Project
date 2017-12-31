package tn.iit.ws.controller.time;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.Semester;

@RestController
@RequestMapping("semester")
public class SemesterController extends GenericController<Semester, Long> {
	
}