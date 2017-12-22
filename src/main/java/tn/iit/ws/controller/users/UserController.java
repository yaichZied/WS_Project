package tn.iit.ws.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.users.User;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.user.UserService;

@RestController
@RequestMapping("user")
public class UserController extends GenericController<User, Long> {
	@Autowired
	private UserService userService;

	@Override
	protected GenericService<User, Long> getService() {
		return userService;
	}
}