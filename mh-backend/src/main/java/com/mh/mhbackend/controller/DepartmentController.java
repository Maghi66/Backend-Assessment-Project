package com.mh.mhbackend.controller;

import com.mh.mhbackend.model.Department;
import com.mh.mhbackend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.function.Function;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(department -> ResponseEntity.ok().body(department))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        if (departmentRepository.findByName(department.getName()).isPresent()) {
            return ResponseEntity.badRequest().body("Department with this name already exists.");
        }
        Department savedDepartment = departmentRepository.save(department);
        return ResponseEntity.ok(savedDepartment);
    }
}
