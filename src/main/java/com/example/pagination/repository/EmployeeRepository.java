package com.example.pagination.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.pagination.model.EmployeeEntity;
import com.example.pagination.projections.EmployeeBasicDetails;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
	 List<EmployeeEntity> findAll(Sort sort);
	 
	 
	 @Query("SELECT e FROM EmployeeEntity e")  // JPA projection using query
	   List<EmployeeBasicDetails> getEmployeeBasicDetails();
	 
	 List<EmployeeBasicDetails> findAllByFirstName(String name);

}
