package com.homework17.skypro.service;

import com.homework17.skypro.model.Employee;
import com.homework17.skypro.exceptions.EmployeeAlreadyAddedException;
import com.homework17.skypro.exceptions.EmployeeNotFoundException;
import com.homework17.skypro.exceptions.EmployeeStorageIsFullException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeService {
    private static final int MAX_EMPLOYEES = 100;
    private final Set<Employee> employees = new HashSet<>();

    public Employee addEmployee(String firstName, String lastName) {
        if (employees.size() >= MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException("Maximum number of employees reached");
        }
        Employee employee = new Employee(firstName, lastName);
        if (!employees.add(employee)) {
            throw new EmployeeAlreadyAddedException("Employee already exists");
        }
        return employee;
    }

    public Employee removeEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.remove(employee)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        return employee;
    }

    public Employee findEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        return employees.stream()
                .filter(e -> e.equals(employee))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    public Set<Employee> getAllEmployees() {
        return new HashSet<>(employees);
    }
}
