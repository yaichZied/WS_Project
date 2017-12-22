package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.Group;
import tn.iit.ws.repositories.GroupRepository;
import tn.iit.ws.service.GenericService;

@Service
public class GroupService extends GenericService<Group, Long> {
	@Autowired
	private GroupRepository groupRepository;

	@Override
	protected JpaRepository<Group, Long> getRepository() {
		return groupRepository;
	}

}
