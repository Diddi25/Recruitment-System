package com.candidate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a candidate's job application: contains details about the candidate,
 * their skills, experience, and availability.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateApplicationModel {
    Integer personId;
    private String skills;
    private BigDecimal experienceYears;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private String status;
    private String firstName;
    private String lastName;

    // Not stored, but used for parsing
    private String candidateName;
}