package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.Level;
import tn.iit.ws.repositories.LevelRepository;
import tn.iit.ws.service.GenericService;

@Service
public class LevelService extends GenericService<Level, Long> {
	@Autowired
	private LevelRepository levelRepository;

	@Override
	protected JpaRepository<Level, Long> getRepository() {
		return levelRepository;
	}

}
