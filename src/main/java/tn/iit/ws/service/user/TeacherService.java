package tn.iit.ws.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.users.Teacher;
import tn.iit.ws.repositories.TeacherRepository;
import tn.iit.ws.service.GenericService;

@Service
public class TeacherService extends GenericService<Teacher, Long> {
	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	protected JpaRepository<Teacher, Long> getRepository() {
		return teacherRepository;
	}

}
