package com.jay.webormmysql.controller;

import com.jay.webormmysql.model.Employee;
import com.jay.webormmysql.repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

	@Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(path = "/employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
}
