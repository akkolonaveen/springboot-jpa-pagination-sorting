package com.example.pagination.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.pagination.model.EmployeeEntity;
import com.example.pagination.projections.EmployeeBasicDetails;
import com.example.pagination.repository.EmployeeRepository;
import com.example.pagination.service.EmployeeService;
import com.example.pagination.utils.CsvUtils;

@RestController
@RequestMapping("/api")
public class EmployeeController 
{
    @Autowired
    EmployeeService service;
    
    @Autowired
    EmployeeRepository repository;
    
    
    //uploading the data into db through choose file option
    @PostMapping(value = "/upload", consumes = "text/csv")
    public void uploadSimple(@RequestBody InputStream body) throws IOException {
        repository.saveAll(CsvUtils.read(EmployeeEntity.class, body));
        
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        repository.saveAll(CsvUtils.read(EmployeeEntity.class, file.getInputStream()));
    }
 //sorting the employees and pagination
    @GetMapping("/employees/sorting")
    public ResponseEntity<Map<String,Object>> getAllEmployees(
                        @RequestParam(defaultValue = "0") Integer pageNo, 
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "id") String sortBy)
                     
    {
        return  service.getAllEmployees(pageNo, pageSize,sortBy);
 
       // return new ResponseEntity<List<EmployeeEntity>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
    //sorting the multiple columns 
    @GetMapping("/employees/groupsort")
    public ResponseEntity<List<EmployeeEntity>> getAll(
    		@RequestParam(defaultValue = "id") Sort sort)
                     
    {
        List<EmployeeEntity> list = service.getAllEmployeesSorted(sort);
 
        return new ResponseEntity<List<EmployeeEntity>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
    
    /////////pagination done================================================
    
    //projection :all employees data
    @GetMapping("/empployee/projections/all")
    public List<EmployeeBasicDetails> getEmployeesData()
    {
    	
    	//ResponseEntity<List<EmployeeBasicDetails>> res
    	return service.getData();
    }
    //projection :all employees by firstname
    @GetMapping("/empployee/projections/{firstname}")
    public List<EmployeeBasicDetails> getFirstNames(@PathVariable String firstname)
    {
    	return service.getFirstNamedata(firstname);
    }
    
  
}