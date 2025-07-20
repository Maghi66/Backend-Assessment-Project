package com.mh.mhbackend.repository;

import com.mh.mhbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);

    // Add method to find employee by email ignoring case
    Employee findByEmailIgnoreCase(String email);
}
