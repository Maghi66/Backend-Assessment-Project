

package com.mh.mhbackend.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mh.mhbackend.model.Employee;
import com.mh.mhbackend.model.ExpenseClaim;
import com.mh.mhbackend.model.ExpenseClaimEntry;
import com.mh.mhbackend.model.ExpenseType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;



import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateExpenseClaim() throws Exception {
        ExpenseType type = new ExpenseType();
        type.setId(1L);

        ExpenseClaimEntry entry = new ExpenseClaimEntry();
        entry.setDate("2025-07-01");
        entry.setDescription("Taxi ride");
        entry.setTotal(50.0);
        entry.setExpenseTypeId(type.getId());

        ExpenseClaim claim = new ExpenseClaim();
        claim.setDate("2025-07-01");
        // Removed claim.setDescription("Business travel"); because ExpenseClaim has no description field
        claim.setTotal(50.0);
        claim.setStatus("PENDING");
        claim.setEmployeeId(1L);
        claim.setEntries(Collections.singletonList(entry));

        mockMvc.perform(post("/api/expenses/claim")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(claim)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(50.0));
    }
    
}
