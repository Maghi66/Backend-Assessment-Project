
package com.mh.mhbackend.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mh.mhbackend.model.Department;
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
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDepartment() throws Exception {
        Department department = new Department();
        department.setName("Marketing");

        mockMvc.perform(post("/api/departments")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Marketing"));
    }
}

