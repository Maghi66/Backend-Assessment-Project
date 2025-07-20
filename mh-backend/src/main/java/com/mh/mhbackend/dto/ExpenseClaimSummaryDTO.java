package com.mh.mhbackend.dto;

public class ExpenseClaimSummaryDTO {
    private Long expenseTypeId;
    private String expenseTypeName;
    private Double totalAmount;

    public ExpenseClaimSummaryDTO() {
    }

    public ExpenseClaimSummaryDTO(Long expenseTypeId, String expenseTypeName, Double totalAmount) {
        this.expenseTypeId = expenseTypeId;
        this.expenseTypeName = expenseTypeName;
        this.totalAmount = totalAmount;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
