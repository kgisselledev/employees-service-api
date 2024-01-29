package com.api.employeesapi.service;

import com.api.employeesapi.model.Employee;
import com.api.employeesapi.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeService {
    @Value("${employee.api.baseurl}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Employee> getAllEmployees() {
        log.info("Lista de empleados: ");
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees){
            double monthlySalary = employee.getEmployee_salary();
            if (monthlySalary != 0.0){
                double annualSalary = calculateAnnualSalary(monthlySalary);
                employee.setEmployee_annual_salary(annualSalary);
            }
        }
       return employees;
    }

    public Employee getEmployeeById(Long employeeId) {
        log.info("Lista empleado con Id: " + employeeId);
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    double monthlySalary = employee.getEmployee_salary();
                    if (monthlySalary != 0.0){
                        double annualSalary = calculateAnnualSalary(monthlySalary);
                        employee.setEmployee_annual_salary(annualSalary);
                    }
                    return employee;
                })
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el empleado con el id: " + employeeId));
    }

    public Double calculateAnnualSalary(Double monthlySalary) {
        log.info("Calculo salario anual: " + monthlySalary);
        return monthlySalary * 12;
    }
}
