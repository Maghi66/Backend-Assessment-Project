
package com.mh.mhbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mh.mhbackend.model.Department;
import com.mh.mhbackend.model.Employee;
import com.mh.mhbackend.model.LeaveType;
import com.mh.mhbackend.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("Alice");
        employee.setEmail("alice@example.com");
        employee.setAddress("Berlin");

        Department type = new Department();
        type.setId(1L);
    

        mockMvc.perform(post("/api/employees")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }
}

