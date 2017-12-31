package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.users.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
