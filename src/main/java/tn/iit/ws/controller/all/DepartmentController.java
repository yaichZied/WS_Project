package tn.iit.ws.controller.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.ws.controller.GenericController;
import tn.iit.ws.entities.all.Department;
import tn.iit.ws.service.GenericService;
import tn.iit.ws.service.all.DepartmentService;

@RestController
@RequestMapping("department")
public class DepartmentController extends GenericController<Department, Long> {
	@Autowired
	private DepartmentService departmentService;

	@Override
	protected GenericService<Department, Long> getService() {
		return departmentService;
	}
}