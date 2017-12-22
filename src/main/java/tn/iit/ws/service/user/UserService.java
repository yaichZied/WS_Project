package tn.iit.ws.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.users.User;
import tn.iit.ws.repositories.UserRepository;
import tn.iit.ws.service.GenericService;

@Service
public class UserService extends GenericService<User, Long> {
	@Autowired
	private UserRepository userRepository;

	@Override
	protected JpaRepository<User, Long> getRepository() {
		return userRepository;
	}

}
