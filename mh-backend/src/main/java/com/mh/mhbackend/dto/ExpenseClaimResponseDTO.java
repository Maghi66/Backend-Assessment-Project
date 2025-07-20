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
public class ExpenseClaimResponseDTO {
    private Long id;
    private String date;
    private double total;
    private String status;
    private Long employeeId;
    private Long expenseTypeId;
    private String expenseTypeName;
    private List<ExpenseClaimEntryDTO> entries;

    public ExpenseClaimResponseDTO(com.mh.mhbackend.model.ExpenseClaim claim) {
        this.id = claim.getId();
        this.date = claim.getDate();
        this.total = claim.getTotal();
        this.status = claim.getStatus();
        this.employeeId = claim.getEmployeeId();
        this.expenseTypeId = claim.getExpenseTypeId();
        this.expenseTypeName = claim.getExpenseTypeName();
        if (claim.getEntries() != null) {
            this.entries = claim.getEntries().stream()
                .map(entry -> new ExpenseClaimEntryDTO(entry, ""))
                .toList();
        }
    }
}
