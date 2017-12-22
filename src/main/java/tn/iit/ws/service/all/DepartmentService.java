package tn.iit.ws.service.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.all.Department;
import tn.iit.ws.repositories.DepartmentRepository;
import tn.iit.ws.service.GenericService;

@Service
public class DepartmentService extends GenericService<Department, Long> {
	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	protected JpaRepository<Department, Long> getRepository() {
		return departmentRepository;
	}

}
