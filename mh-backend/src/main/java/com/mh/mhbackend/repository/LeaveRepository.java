package com.mh.mhbackend.repository;

import com.mh.mhbackend.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployeeIdAndFromDateBetween(Long employeeId, String from, String to);
    List<Leave> findByLeaveTypeIdAndEmployeeId(Long typeId, Long employeeId);

    @Query("SELECT SUM(l.numberOfDays) FROM Leave l WHERE l.employeeId = :employeeId AND l.leaveTypeId = :typeId AND l.fromDate >= :from AND l.toDate <= :to")
    Integer getTotalLeaveDaysByEmployeeAndType(
        @Param("employeeId") Long employeeId,
        @Param("typeId") Long typeId,
        @Param("from") String from,
        @Param("to") String to
    );
}


