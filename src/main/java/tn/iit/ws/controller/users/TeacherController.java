package tn.iit.ws.controller.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.users.Teacher;

@RestController
@RequestMapping("teacher")
public class TeacherController extends GenericController<Teacher, Long> {
	
}