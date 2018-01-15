package tn.iit.ws.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.all.Course;
import tn.iit.ws.entities.all.Pointing;

@Repository
public interface PointingRepository extends JpaRepository<Pointing, Long> {
	List<Pointing> findByCourseAndDateBetween(Course course, Date date1, Date date2);
}
