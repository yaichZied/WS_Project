package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Level;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.LevelService;

@RestController
@RequestMapping("level")
public class LevelController extends GenericController<Level, Long> {
	@Autowired
	private LevelService levelService;

	@Override
	protected GenericService<Level, Long> getService() {
		return levelService;
	}
}