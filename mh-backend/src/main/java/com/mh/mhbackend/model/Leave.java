package com.mh.mhbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "leave_type_id")
    private Long leaveTypeId;

    @Column(name = "employee_id")
    private Long employeeId;

    private String fromDate;
    private String toDate;

    @JsonProperty(access = Access.READ_ONLY)
    private int numberOfDays;

    private String note;
}
