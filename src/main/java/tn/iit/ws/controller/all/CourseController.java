package tn.iit.ws.controller.all;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Course;
import tn.iit.ws.repositories.CourseRepository;
import tn.iit.ws.repositories.PointingRepository;

@RestController
@RequestMapping("course")
public class CourseController extends GenericController<Course, Long> {
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private PointingRepository pointingRepository;

	@SuppressWarnings("deprecation")
	@RequestMapping(path = "pointing", method = RequestMethod.GET)
	public List<Course> listPointing(@RequestParam(name = "date", required = false) Long timeStamp) {
		final Date date = (timeStamp != null && timeStamp > 1000) ? new Date(timeStamp) : new Date();
		final Date date1 = new Date(date.getTime());
		date1.setHours(0);
		date1.setMinutes(0);
		date1.setSeconds(0);
		final Date date2 = new Date(date1.getTime());
		date2.setDate(date2.getDate()-1);
		return courseRepository.coursesByDate(date.getDay(),date.getHours()*60+date.getMinutes(),date).stream().filter(course -> {
			return pointingRepository.findByCourseAndDateBetween(course, date1,date2).size()==0;
		}).collect(Collectors.toList());
	}
}
