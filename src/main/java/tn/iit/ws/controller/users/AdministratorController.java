package tn.iit.ws.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.users.Administrator;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.user.AdministratorService;

@RestController
@RequestMapping("administrator")
public class AdministratorController extends GenericController<Administrator, Long> {
	@Autowired
	private AdministratorService administratorService;

	@Override
	protected GenericService<Administrator, Long> getService() {
		return administratorService;
	}
}