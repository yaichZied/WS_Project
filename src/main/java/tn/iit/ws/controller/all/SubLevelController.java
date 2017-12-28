package tn.iit.ws.controller.all;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.SubLevel;

@RestController
@RequestMapping("sublevel")
public class SubLevelController extends GenericController<SubLevel, Long> {
	
}