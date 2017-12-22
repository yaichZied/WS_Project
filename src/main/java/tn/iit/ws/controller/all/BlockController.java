package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Block;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.BlockService;

@RestController
@RequestMapping("block")
public class BlockController extends GenericController<Block, Long> {
	@Autowired
	private BlockService blockService;

	@Override
	protected GenericService<Block, Long> getService() {
		return blockService;
	}
}