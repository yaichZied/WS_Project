package tn.iit.ws.controller.time;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.time.Vacation;

@RestController
@RequestMapping("vaction")
public class VacationController extends GenericController<Vacation, Long> {
	
}