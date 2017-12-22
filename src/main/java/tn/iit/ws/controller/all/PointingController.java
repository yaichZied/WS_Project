package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Pointing;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.PointingService;

@RestController
@RequestMapping("pointing")
public class PointingController extends GenericController<Pointing, Long> {
	@Autowired
	private PointingService pointingService;

	@Override
	protected GenericService<Pointing, Long> getService() {
		return pointingService;
	}
}