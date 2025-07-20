package com.mh.mhbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExpenseClaimEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String date;
    private String description;
    private double total;

    @Column(name = "expense_type_id")
    private Long expenseTypeId;

    @Column(name = "expense_claim_id")
    private Long expenseClaimId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_claim_id", insertable = false, updatable = false)
    @com.fasterxml.jackson.annotation.JsonBackReference
    private ExpenseClaim expenseClaim;

}
