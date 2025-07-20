package com.mh.mhbackend.controller;

import com.mh.mhbackend.model.Leave;
import com.mh.mhbackend.model.LeaveType;
import com.mh.mhbackend.repository.LeaveRepository;
import com.mh.mhbackend.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @PostMapping
    public Leave createLeave(@RequestBody Leave leave) {
        // Calculate numberOfDays from fromDate and toDate
        if (leave.getFromDate() != null && leave.getToDate() != null) {
            try {
                java.time.LocalDate from = java.time.LocalDate.parse(leave.getFromDate());
                java.time.LocalDate to = java.time.LocalDate.parse(leave.getToDate());
                long days = java.time.temporal.ChronoUnit.DAYS.between(from, to) + 1;
                leave.setNumberOfDays((int) days);
            } catch (Exception e) {
                // Handle parse exception or invalid dates
                leave.setNumberOfDays(0);
            }
        } else {
            leave.setNumberOfDays(0);
        }
        return leaveRepository.save(leave);
    }

    @GetMapping("")
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    @GetMapping("/type")
    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeRepository.findAll();
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<LeaveType> getLeaveTypeById(@PathVariable Long id) {
        return leaveTypeRepository.findById(id)
                .map(leaveType -> ResponseEntity.ok().body(leaveType))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{id}")
    public List<Leave> getLeavesByEmployeeId(@PathVariable Long id) {
        return leaveRepository.findByEmployeeIdAndFromDateBetween(id, "1900-01-01", "2100-01-01");
    }

    @GetMapping("/employee/{id}/range")
    public List<Leave> getLeavesByEmployeeIdAndDateRange(
        @PathVariable Long id,
        @RequestParam String from,
        @RequestParam String to
    ) {
        return leaveRepository.findByEmployeeIdAndFromDateBetween(id, from, to);
    }

    @PostMapping("/type")
    public ResponseEntity<?> createLeaveType(@RequestBody LeaveType leaveType) {
        if (leaveTypeRepository.findByNameIgnoreCase(leaveType.getName()).isPresent()) {
            return ResponseEntity.badRequest().body("Leave type with this name already exists.");
        }
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);
        return ResponseEntity.ok(savedLeaveType);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteLeavesByEmployeeId(@PathVariable Long id) {
        leaveRepository.deleteAll(leaveRepository.findByEmployeeIdAndFromDateBetween(id, "1900-01-01", "2100-01-01"));
        return ResponseEntity.ok().body("Leaves deleted for employee id: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLeave(@PathVariable("id") Long id, @RequestBody Leave updatedLeave) {
        return leaveRepository.findById(id).map(leave -> {
            leave.setFromDate(updatedLeave.getFromDate());
            leave.setToDate(updatedLeave.getToDate());
            leave.setLeaveTypeId(updatedLeave.getLeaveTypeId());
            // Recalculate numberOfDays
            if (leave.getFromDate() != null && leave.getToDate() != null) {
                try {
                    java.time.LocalDate from = java.time.LocalDate.parse(leave.getFromDate());
                    java.time.LocalDate to = java.time.LocalDate.parse(leave.getToDate());
                    long days = java.time.temporal.ChronoUnit.DAYS.between(from, to) + 1;
                    leave.setNumberOfDays((int) days);
                } catch (Exception e) {
                    leave.setNumberOfDays(0);
                }
            } else {
                leave.setNumberOfDays(0);
            }
            Leave savedLeave = leaveRepository.save(leave);
            return ResponseEntity.ok(savedLeave);
        }).orElse(ResponseEntity.notFound().build());
    }
}
