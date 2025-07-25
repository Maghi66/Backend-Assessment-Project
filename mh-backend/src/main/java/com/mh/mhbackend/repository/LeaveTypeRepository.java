package com.mh.mhbackend.repository;

import com.mh.mhbackend.model.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    Optional<LeaveType> findByNameIgnoreCase(String name);
}
