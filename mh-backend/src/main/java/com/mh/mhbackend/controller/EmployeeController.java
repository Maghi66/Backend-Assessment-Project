package com.mh.mhbackend.controller;

import com.mh.mhbackend.model.Employee;
import com.mh.mhbackend.repository.EmployeeRepository;
import com.mh.mhbackend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{deptId}")
    public List<Employee> getEmployeesByDepartment(@PathVariable Long deptId) {
        return employeeRepository.findByDepartmentId(deptId);
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        if (employeeRepository.findByEmailIgnoreCase(employee.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Employee with this email already exists.");
        }
        // Ensure the employee is new by clearing the id to avoid stale state issues
        employee.setId(null);
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setName(updatedEmployee.getName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setAddress(updatedEmployee.getAddress());
        employee.setDepartmentId(updatedEmployee.getDepartmentId());
        return employeeRepository.save(employee);
    }

    // Removed DELETE endpoint as per request
}
