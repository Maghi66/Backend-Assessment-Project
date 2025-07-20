package com.mh.mhbackend.repository;
import java.util.Optional;

import com.mh.mhbackend.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
    Optional<ExpenseType> findByName(String name);
}
