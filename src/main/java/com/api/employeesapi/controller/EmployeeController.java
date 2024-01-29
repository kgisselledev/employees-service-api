package com.api.employeesapi.controller;


import com.api.employeesapi.model.Employee;
import com.api.employeesapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){

        return employeeService.getAllEmployees();

    }
    @GetMapping("/employees/{id}")
    public Employee getEmployeeId(@PathVariable Long id) {

        return employeeService.getEmployeeById(id);
    }

}
