package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.Block;
import tn.iit.ws.repositories.BlockRepository;
import tn.iit.ws.service.GenericService;

@Service
public class BlockService extends GenericService<Block, Long> {
	@Autowired
	private BlockRepository blockRepository;

	@Override
	protected JpaRepository<Block, Long> getRepository() {
		return blockRepository;
	}

}
