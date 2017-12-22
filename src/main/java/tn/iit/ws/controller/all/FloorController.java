package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Floor;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.FloorService;

@RestController
@RequestMapping("floor")
public class FloorController extends GenericController<Floor, Long> {
	@Autowired
	private FloorService floorService;

	@Override
	protected GenericService<Floor, Long> getService() {
		return floorService;
	}
}