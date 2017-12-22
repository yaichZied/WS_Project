package tn.iit.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.entities.all.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
}
