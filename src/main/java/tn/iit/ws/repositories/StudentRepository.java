package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.users.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
}
