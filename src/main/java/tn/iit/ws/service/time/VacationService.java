package tn.iit.ws.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.time.Vacation;
import tn.iit.ws.repositories.VacationRepository;
import tn.iit.ws.service.GenericService;

@Service
public class VacationService extends GenericService<Vacation, Long> {
	@Autowired
	private VacationRepository vacationRepository;

	@Override
	protected JpaRepository<Vacation, Long> getRepository() {
		return vacationRepository;
	}

}
