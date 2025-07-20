package com.mh.mhbackend.controller;

import com.mh.mhbackend.model.*;
import com.mh.mhbackend.repository.*;
import com.mh.mhbackend.dto.ExpenseClaimEntryDTO;
import com.mh.mhbackend.dto.ExpenseClaimResponseDTO;
import com.mh.mhbackend.dto.ExpenseClaimSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mh.mhbackend.service.ExpenseTypeService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private ExpenseClaimRepository expenseClaimRepository;

    @Autowired
    private ExpenseClaimEntryRepository expenseClaimEntryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ExpenseTypeService expenseTypeService;

    // Define expense type
    @PostMapping("/type")
    public ExpenseType createExpenseType(@RequestBody ExpenseType expenseType) {
        return expenseTypeService.createExpenseType(expenseType);
    }

    // Get all expense types
    @GetMapping("/type")
    public List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeService.getAllExpenseTypes();
    }

    // Get expense claims by expense type id with entries
    @GetMapping("/type/{expenseTypeId}")
    public List<ExpenseClaimResponseDTO> getClaimsByExpenseTypeId(@PathVariable Long expenseTypeId) {
        List<ExpenseClaim> claims = expenseClaimRepository.findByExpenseTypeIdWithEntries(expenseTypeId);
        return claims.stream().map(claim -> {
            // Fetch expenseTypeName for each claim
            String expenseTypeName = "";
            if (claim.getExpenseTypeId() != null) {
                var expenseType = expenseTypeService.getExpenseTypeById(claim.getExpenseTypeId());
                if (expenseType != null) {
                    expenseTypeName = expenseType.getName();
                }
            }
            claim.setExpenseTypeName(expenseTypeName);

            // Map entries to DTOs
            List<com.mh.mhbackend.dto.ExpenseClaimEntryDTO> entryDTOs = claim.getEntries().stream()
                .map(entry -> new com.mh.mhbackend.dto.ExpenseClaimEntryDTO(entry, ""))
                .collect(Collectors.toList());

            com.mh.mhbackend.dto.ExpenseClaimResponseDTO responseDTO = new com.mh.mhbackend.dto.ExpenseClaimResponseDTO(claim);
            responseDTO.setEntries(entryDTOs);
            return responseDTO;
        }).collect(Collectors.toList());
    }

    // Submit a new expense claim
    @PostMapping("/claim")
    public com.mh.mhbackend.dto.ExpenseClaimResponseDTO submitExpenseClaim(@RequestBody com.mh.mhbackend.dto.ExpenseClaimRequestDTO claimDTO) {
        // Validate request format and required fields
        if (claimDTO == null || claimDTO.getEmployeeId() == null || claimDTO.getDate() == null || claimDTO.getEntries() == null) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Invalid request format. Required fields: employeeId, date, entries.");
        }
        ExpenseClaim claim = new ExpenseClaim();
        claim.setDate(claimDTO.getDate());
        claim.setStatus("pending"); // default status
        claim.setEmployeeId(claimDTO.getEmployeeId());
        claim.setExpenseTypeId(claimDTO.getExpenseTypeId());

        // Save claim without entries first
        ExpenseClaim savedClaim = expenseClaimRepository.save(claim);

        double total = 0;
        if (claimDTO.getEntries() != null) {
            for (com.mh.mhbackend.dto.ExpenseClaimEntryRequestDTO entryDTO : claimDTO.getEntries()) {
                ExpenseClaimEntry entry = new ExpenseClaimEntry();
                entry.setDate(entryDTO.getDate());
                entry.setDescription(entryDTO.getDescription());
                entry.setTotal(entryDTO.getTotal());
                entry.setExpenseClaimId(savedClaim.getId());
                expenseClaimEntryRepository.save(entry);
                total += entry.getTotal();
            }
        }

        savedClaim.setTotal(total);
        savedClaim = expenseClaimRepository.save(savedClaim);

        // Reload savedClaim to get updated entries
        savedClaim = expenseClaimRepository.findByIdWithEntries(savedClaim.getId()).orElse(savedClaim);

        // Fetch expenseTypeName for response
        String expenseTypeName = "";
        Long expenseTypeId = claimDTO.getExpenseTypeId();
        if (expenseTypeId != null) {
            var expenseType = expenseTypeService.getExpenseTypeById(expenseTypeId);
            if (expenseType != null) {
                expenseTypeName = expenseType.getName();
            }
            savedClaim.setExpenseTypeId(expenseTypeId);
        }
        savedClaim.setExpenseTypeName(expenseTypeName);

        // Save again to persist expenseTypeName in DB
        savedClaim = expenseClaimRepository.save(savedClaim);

        // Map entries to DTOs with expenseTypeId and expenseTypeName
        List<com.mh.mhbackend.dto.ExpenseClaimEntryDTO> entryDTOs = savedClaim.getEntries().stream()
            .map(entry -> {
                return new com.mh.mhbackend.dto.ExpenseClaimEntryDTO(entry, "");
            })
            .collect(Collectors.toList());

        com.mh.mhbackend.dto.ExpenseClaimResponseDTO responseDTO = new com.mh.mhbackend.dto.ExpenseClaimResponseDTO(savedClaim);
        responseDTO.setEntries(entryDTOs);

        return responseDTO;
    }

    /*
    // Get claims by employee
    @GetMapping("/employee/{id}")
    public List<ExpenseClaim> getClaimsByEmployee(@PathVariable Long id) {
        return expenseClaimRepository.findByEmployeeId(id);
    }
    */

    @GetMapping("/expense-claims/employee/{employeeId}")
    public List<ExpenseClaimResponseDTO> getClaimsByEmployeeId(@PathVariable Long employeeId) {
        List<ExpenseClaim> claims = expenseClaimRepository.findByEmployeeIdWithEntries(employeeId);
        return claims.stream().map(claim -> {
            // Fetch expenseTypeName for each claim
            String expenseTypeName = "";
            if (claim.getExpenseTypeId() != null) {
                var expenseType = expenseTypeService.getExpenseTypeById(claim.getExpenseTypeId());
                if (expenseType != null) {
                    expenseTypeName = expenseType.getName();
                }
            }
            claim.setExpenseTypeName(expenseTypeName);

            // Map entries to DTOs
            List<com.mh.mhbackend.dto.ExpenseClaimEntryDTO> entryDTOs = claim.getEntries().stream()
                .map(entry -> new com.mh.mhbackend.dto.ExpenseClaimEntryDTO(entry, ""))
                .collect(Collectors.toList());

            com.mh.mhbackend.dto.ExpenseClaimResponseDTO responseDTO = new com.mh.mhbackend.dto.ExpenseClaimResponseDTO(claim);
            responseDTO.setEntries(entryDTOs);
            return responseDTO;
        }).collect(Collectors.toList());
    }


    /*
    @GetMapping("/total")
    public Double getTotalExpenseByTypeAndEmployee(
        @RequestParam Long typeId,
        @RequestParam Long employeeId
    ) {
        return expenseClaimEntryRepository.getTotalExpenseByTypeAndEmployee(typeId, employeeId);
    }
    */

    // Remove or restrict access to expense claim entries endpoint to make ExpenseClaimEntry read-only and not accessible by users
    // Commenting out the endpoint to restrict access
    /*
    @GetMapping("/entries")
    public List<ExpenseClaimEntryDTO> getAllExpenseClaimEntries() {
        List<ExpenseClaimEntry> entries = expenseClaimEntryRepository.findAll();
        return entries.stream()
                .map(ExpenseClaimEntryDTO::new)
                .collect(Collectors.toList());
    }
    */

}
