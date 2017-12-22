package tn.iit.ws.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.users.Student;
import tn.iit.ws.repositories.StudentRepository;
import tn.iit.ws.service.GenericService;

@Service
public class StudentService extends GenericService<Student, Long> {
	@Autowired
	private StudentRepository studentRepository;

	@Override
	protected JpaRepository<Student, Long> getRepository() {
		return studentRepository;
	}

}
