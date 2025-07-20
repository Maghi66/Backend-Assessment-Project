package com.mh.mhbackend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseClaimRequestDTO {
    private String date;
    private String status;
    private Long employeeId;
    private Long expenseTypeId;
    private List<ExpenseClaimEntryRequestDTO> entries;
}
