package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Course;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.CourseService;

@RestController
@RequestMapping("course")
public class CourseController extends GenericController<Course, Long> {
	@Autowired
	private CourseService courseService;

	@Override
	protected GenericService<Course, Long> getService() {
		return courseService;
	}
}