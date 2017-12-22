package tn.iit.ws.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.time.InterruptionPeriod;
import tn.iit.ws.repositories.InterruptionPeriodRepository;
import tn.iit.ws.service.GenericService;

@Service
public class InterruptionPeriodService extends GenericService<InterruptionPeriod, Long> {
	@Autowired
	private InterruptionPeriodRepository interruptionPeriodRepository;

	@Override
	protected JpaRepository<InterruptionPeriod, Long> getRepository() {
		return interruptionPeriodRepository;
	}

}
