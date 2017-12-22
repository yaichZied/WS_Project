package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Group;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.GroupService;

@RestController
@RequestMapping("group")
public class GroupController extends GenericController<Group, Long> {
	@Autowired
	private GroupService groupService;

	@Override
	protected GenericService<Group, Long> getService() {
		return groupService;
	}
}