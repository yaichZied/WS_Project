package tn.iit.ws.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.all.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	@Query("Select c FROM Course c WHERE "
			+ "(( (c.timeSlot.semester.begin) < ?3 AND (c.timeSlot.semester.end) > ?3 ) AND "
			+ "((c.timeSlot.day = ?1 "
				+ "AND ( c.timeSlot.begin <= ?2 ) "
				+ "AND ( c.timeSlot.end >= ?2)) "
			+ "OR ((c.timeSlot.date) <= ?3 AND ((c.timeSlot.date) + (c.timeSlot.duration) )>= ?3 ))) "
			+ " AND (SELECT count(p) FROM Pointing p WHERE ((p.date) + 86400000 )> ?3 AND p.course = c) = 0")
	List<Course> coursesByDate(Integer day,Integer time,Date date);
}
