package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.Subject;
import tn.iit.ws.repositories.SubjectRepository;
import tn.iit.ws.service.GenericService;

@Service
public class SubjectService extends GenericService<Subject, Long> {
	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	protected JpaRepository<Subject, Long> getRepository() {
		return subjectRepository;
	}

}
