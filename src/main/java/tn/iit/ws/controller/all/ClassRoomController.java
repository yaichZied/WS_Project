package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.ClassRoom;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.ClassRoomService;

@RestController
@RequestMapping("classroom")
public class ClassRoomController extends GenericController<ClassRoom, Long> {
	@Autowired
	private ClassRoomService classRoomService;

	@Override
	protected GenericService<ClassRoom, Long> getService() {
		return classRoomService;
	}
}