package tn.iit.ws.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.users.Student;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.user.StudentService;

@RestController
@RequestMapping("student")
public class StudentController extends GenericController<Student, Long> {
	@Autowired
	private StudentService studentService;

	@Override
	protected GenericService<Student, Long> getService() {
		return studentService;
	}
}