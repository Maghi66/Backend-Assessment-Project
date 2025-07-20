package com.mh.mhbackend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseClaimEntryRequestDTO {
    private String date;
    private String description;
    private double total; // optional, can be omitted if total is always calculated on server
}
