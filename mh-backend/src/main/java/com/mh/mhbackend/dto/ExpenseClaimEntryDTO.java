package com.mh.mhbackend.dto;

import com.mh.mhbackend.model.ExpenseClaimEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseClaimEntryDTO {

    private Long id;
    private String date;
    private String description;
    private double total;

    public ExpenseClaimEntryDTO(ExpenseClaimEntry entry, String expenseTypeName) {
        this.id = entry.getId();
        this.date = entry.getDate();
        this.description = entry.getDescription();
        this.total = entry.getTotal();
    }
}
