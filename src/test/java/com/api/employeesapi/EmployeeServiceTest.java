package com.api.employeesapi;

import com.api.employeesapi.model.Employee;
import com.api.employeesapi.repository.EmployeeRepository;
import com.api.employeesapi.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class EmployeeServiceTest {
    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceTest.class);
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees){
            double monthlySalary = employee.getEmployee_salary();
            if (monthlySalary != 0.0){
                double annualSalary = employeeService.calculateAnnualSalary(monthlySalary);
                employee.setEmployee_annual_salary(annualSalary);
                employeeRepository.save(employee);
            }
        }

        employees = employeeRepository.findAll();
        log.info("Lista de empleados test: " + employees);

        for (Employee employee : employees) {
            assertEquals(employee.getEmployee_annual_salary(), employeeService.calculateAnnualSalary(employee.getEmployee_salary()));
        }
    }

    @Test
    public void testgetEmployeeById(){

        Employee savedEmployee = saveEmployeeForTest();

        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());
        assertTrue(optionalEmployee.isPresent(), "El empleado no se encontró por ID");
        Employee employee = optionalEmployee.get();
        log.info("Lista de empleado id test: " + employee);
        double monthlySalary = employee.getEmployee_salary();
        if (monthlySalary != 0.0) {
            double expectedAnnualSalary = employeeService.calculateAnnualSalary(monthlySalary);
            employee.setEmployee_annual_salary(expectedAnnualSalary);
            employeeRepository.save(employee);

            assertEquals(expectedAnnualSalary, employee.getEmployee_annual_salary());
        }
    }

    private Employee saveEmployeeForTest() {
        Employee employee = new Employee();
        employee.setEmployee_name("Karol Ramirez");
        employee.setEmployee_age("30");
        employee.setEmployee_salary(25000.0);
        return employeeRepository.save(employee);
    }

    @Test
    public void testcalculateAnnualSalary(){
        double monthlySalary = 170750.0;
        double annualSalary = employeeService.calculateAnnualSalary(monthlySalary);
        assertEquals(2049000.0, annualSalary);
        log.info("Salario mensual test: " + monthlySalary);
        log.info("Salario anual test: " + annualSalary);
    }
}
