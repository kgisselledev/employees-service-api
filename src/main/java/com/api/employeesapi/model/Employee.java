package com.api.employeesapi.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name= "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name ="EMPLOYEE_NAME")
    private String employee_name;
    @Column(name = "EMPLOYEE_AGE")
    private String employee_age;
    @Column(name = "EMPLOYEE_SALARY")
    private double employee_salary;
    @Transient
    private double employee_annual_salary= 0.0;

}
