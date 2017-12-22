package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.SubLevel;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.SubLevelService;

@RestController
@RequestMapping("sublevel")
public class SubLevelController extends GenericController<SubLevel, Long> {
	@Autowired
	private SubLevelService subLevelService;

	@Override
	protected GenericService<SubLevel, Long> getService() {
		return subLevelService;
	}
}