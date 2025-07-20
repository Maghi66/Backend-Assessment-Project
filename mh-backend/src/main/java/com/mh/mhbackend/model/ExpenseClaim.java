package com.mh.mhbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExpenseClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String date;
    
    @com.fasterxml.jackson.annotation.JsonProperty(access = com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY)
    private double total;
    private String status;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "expense_type_id")
    private Long expenseTypeId;

    @Column(name = "expense_type_name")
    private String expenseTypeName;

    @OneToMany(mappedBy = "expenseClaim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpenseClaimEntry> entries = new ArrayList<>();
}
