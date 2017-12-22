package tn.iit.ws.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.time.Semester;
import tn.iit.ws.repositories.SemesterRepository;
import tn.iit.ws.service.GenericService;

@Service
public class SemesterService extends GenericService<Semester, Long> {
	@Autowired
	private SemesterRepository semesterRepository;

	@Override
	protected JpaRepository<Semester, Long> getRepository() {
		return semesterRepository;
	}

}
