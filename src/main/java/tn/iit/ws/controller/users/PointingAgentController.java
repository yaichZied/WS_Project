package tn.iit.ws.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.users.PointingAgent;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.user.PointingAgentService;

@RestController
@RequestMapping("pointingAgent")
public class PointingAgentController extends GenericController<PointingAgent, Long> {
	@Autowired
	private PointingAgentService pointingAgentService;

	@Override
	protected GenericService<PointingAgent, Long> getService() {
		return pointingAgentService;
	}
}