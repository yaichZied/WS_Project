package tn.iit.ws.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.time.ExamsPeriod;
import tn.iit.ws.repositories.ExamsPeriodRepository;
import tn.iit.ws.service.GenericService;

@Service
public class ExamPeriodService extends GenericService<ExamsPeriod, Long> {
	@Autowired
	private ExamsPeriodRepository examPeriodRepository;

	@Override
	protected JpaRepository<ExamsPeriod, Long> getRepository() {
		return examPeriodRepository;
	}

}
