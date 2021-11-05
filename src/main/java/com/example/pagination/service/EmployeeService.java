package com.example.pagination.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.pagination.model.EmployeeEntity;
import com.example.pagination.projections.EmployeeBasicDetails;
import com.example.pagination.repository.EmployeeRepository;

@Service
public class EmployeeService 
{
    @Autowired
    EmployeeRepository repository;
     
    public ResponseEntity<Map<String, Object>> getAllEmployees(Integer pageNo, Integer pageSize, String sort)
    {
    	
    	try {
   
        List<EmployeeEntity> data = new ArrayList<EmployeeEntity>();
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        
 
        Page<EmployeeEntity> pagedResult = repository.findAll(paging);
       data= pagedResult.getContent();
        
     Map<String, Object> response = new HashMap<>();
     response.put("employee  ", data);
     response.put("currentPage ", pagedResult.getNumber());
     response.put("totalItems ", pagedResult.getTotalElements());
     response.put("totalPages ", pagedResult.getTotalPages());
        
         
     return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
   public List<EmployeeEntity> getAll(Sort groupBy){
    	
    	Sort emailSort = Sort.by("email"); 
    	Sort firstNameSort = Sort.by("first_name"); 
    	Sort lastNameSort = Sort.by("first_name"); 
    	Sort groupBySort = emailSort.and(firstNameSort).and(lastNameSort);
    	List<EmployeeEntity> list = repository.findAll(groupBySort);
    	return list;
     /*  Pageable paging = PageRequest.of(pageNo, pageSize,  Sort.by(sort));
 
       Page<EmployeeEntity> pagedResult = repository.findAll(paging);
    	
         
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<EmployeeEntity>();
        }*/
    }

public List<EmployeeEntity> getAllEmployeesSorted(Sort sort) {
	Sort emailSort = Sort.by("email"); 
	Sort firstNameSort = Sort.by("first_name"); 
	Sort lastNameSort = Sort.by("first_name"); 
	Sort groupBySort = emailSort.and(firstNameSort).and(lastNameSort);
	List<EmployeeEntity > list = repository.findAll(groupBySort);
	return list;
}

public List<EmployeeBasicDetails> getData() {
	
	return repository.getEmployeeBasicDetails();
}

public List<EmployeeBasicDetails> getFirstNamedata(String firstname) {
	
	return (List<EmployeeBasicDetails>) repository.findAllByFirstName(firstname);
}
}
