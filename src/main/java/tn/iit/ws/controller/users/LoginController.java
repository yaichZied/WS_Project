package tn.iit.ws.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.entities.users.User;
import tn.iit.ws.repositories.UserRepository;

@RestController
public class LoginController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/me")
	public User me() {
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		return userRepository.findByUsername(username);
	}
}
