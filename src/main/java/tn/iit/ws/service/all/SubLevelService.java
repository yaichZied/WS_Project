package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.SubLevel;
import tn.iit.ws.repositories.SubLevelRepository;
import tn.iit.ws.service.GenericService;

@Service
public class SubLevelService extends GenericService<SubLevel, Long> {
	@Autowired
	private SubLevelRepository subLevelRepository;

	@Override
	protected JpaRepository<SubLevel, Long> getRepository() {
		return subLevelRepository;
	}

}
