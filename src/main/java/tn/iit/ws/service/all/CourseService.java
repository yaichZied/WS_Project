package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.Course;
import tn.iit.ws.repositories.CourseRepository;
import tn.iit.ws.service.GenericService;

@Service
public class CourseService extends GenericService<Course, Long> {
	@Autowired
	private CourseRepository courseRepository;

	@Override
	protected JpaRepository<Course, Long> getRepository() {
		return courseRepository;
	}

}
