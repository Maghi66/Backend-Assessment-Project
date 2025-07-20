

package com.mh.mhbackend.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mh.mhbackend.model.Employee;
import com.mh.mhbackend.model.Leave;
import com.mh.mhbackend.model.LeaveType;
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
public class LeaveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateLeave() throws Exception {
        Leave leave = new Leave();
        leave.setFromDate("2025-07-01");
        leave.setToDate("2025-07-05");
        leave.setNumberOfDays(5);
        leave.setNote("Test Leave");

        LeaveType type = new LeaveType();
        type.setId(1L);

        leave.setEmployeeId(1L);
        // Remove the call to setLeaveType since it does not exist in Leave class
        leave.setLeaveTypeId(type.getId());

        mockMvc.perform(post("/api/leaves")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(leave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfDays").value(5));
    }
}

