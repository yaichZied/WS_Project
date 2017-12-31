package tn.iit.ws.controller.all;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Level;

@RestController
@RequestMapping("level")
public class LevelController extends GenericController<Level, Long> {
	
}