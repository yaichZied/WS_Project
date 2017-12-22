package tn.iit.ws.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tn.iit.ws.entities.users.Administrator;
import tn.iit.ws.repositories.UserRepository;

@Component
public class InitComponent {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@PostConstruct
	private void init() {
		if(userRepository.count()==0)
		{
			Administrator admin = new Administrator();
			admin.setEmail("admin@admin.com");
			admin.setName("Administrateur");
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("password"));
			userRepository.save(admin);
		}
		
	}
}
