package com.mh.mhbackend.repository;

import com.mh.mhbackend.model.ExpenseClaimEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseClaimEntryRepository extends JpaRepository<ExpenseClaimEntry, Long> {
    @Query("SELECT SUM(e.total) FROM ExpenseClaimEntry e JOIN e.expenseClaim c WHERE e.expenseTypeId = :typeId AND c.employeeId = :employeeId")
    Double getTotalExpenseByTypeAndEmployee(
        @Param("typeId") Long typeId,
        @Param("employeeId") Long employeeId
    );

    @Query("SELECT e.expenseTypeId as expenseTypeId, SUM(e.total) as totalAmount FROM ExpenseClaimEntry e JOIN e.expenseClaim c WHERE c.employeeId = :employeeId GROUP BY e.expenseTypeId")
    java.util.List<Object[]> findTotalAmountGroupedByExpenseType(@Param("employeeId") Long employeeId);

    @Query("SELECT et.id as expenseTypeId, et.name as expenseTypeName, SUM(e.total) as totalAmount " +
           "FROM ExpenseClaimEntry e " +
           "JOIN e.expenseClaim c " +
           "JOIN ExpenseType et ON et.id = e.expenseTypeId " +
           "WHERE c.employeeId = :employeeId " +
           "GROUP BY et.id, et.name")
    java.util.List<Object[]> findTotalAmountGroupedByExpenseTypeWithName(@Param("employeeId") Long employeeId);

}
