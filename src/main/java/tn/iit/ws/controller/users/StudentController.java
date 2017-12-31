package tn.iit.ws.controller.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.users.Student;

@RestController
@RequestMapping("student")
public class StudentController extends GenericController<Student, Long> {
	
}