package tn.iit.ws.controller.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.users.PointingAgent;

@RestController
@RequestMapping("pointingAgent")
public class PointingAgentController extends GenericController<PointingAgent, Long> {
	
}