package tn.iit.ws.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.users.Teacher;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.user.TeacherService;

@RestController
@RequestMapping("teacher")
public class TeacherController extends GenericController<Teacher, Long> {
	@Autowired
	private TeacherService teacherService;

	@Override
	protected GenericService<Teacher, Long> getService() {
		return teacherService;
	}
}