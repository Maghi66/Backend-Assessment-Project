package com.mh.mhbackend.service;

import com.mh.mhbackend.model.ExpenseType;
import com.mh.mhbackend.repository.ExpenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    public ExpenseType createExpenseType(ExpenseType expenseType) {
        Optional<ExpenseType> existing = expenseTypeRepository.findByName(expenseType.getName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Expense type already exists with name: " + expenseType.getName());
        }
        return expenseTypeRepository.save(expenseType);
    }

    public java.util.List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeRepository.findAll();
    }

    public ExpenseType getExpenseTypeById(Long id) {
        return expenseTypeRepository.findById(id).orElse(null);
    }
}
