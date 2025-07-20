package com.mh.mhbackend.repository;

import com.mh.mhbackend.model.ExpenseClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface ExpenseClaimRepository extends JpaRepository<ExpenseClaim, Long> {
    List<ExpenseClaim> findByEmployeeId(Long employeeId);
    @Query("SELECT DISTINCT ec FROM ExpenseClaim ec LEFT JOIN FETCH ec.entries WHERE ec.employeeId = :employeeId")
    List<ExpenseClaim> findByEmployeeIdWithEntries(@Param("employeeId") Long employeeId);

    @Query("SELECT ec FROM ExpenseClaim ec LEFT JOIN FETCH ec.entries WHERE ec.id = :id")
    java.util.Optional<ExpenseClaim> findByIdWithEntries(@Param("id") Long id);

    @Query("SELECT DISTINCT ec FROM ExpenseClaim ec LEFT JOIN FETCH ec.entries WHERE ec.expenseTypeId = :expenseTypeId")
    java.util.List<ExpenseClaim> findByExpenseTypeIdWithEntries(@Param("expenseTypeId") Long expenseTypeId);
}
