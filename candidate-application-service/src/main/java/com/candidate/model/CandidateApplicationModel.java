package com.candidate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;

/**
 * Represents a candidate's job application.
 * This model contains details about the candidate, their skills,
 * experience, and availability.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateApplicationModel {
    private int id;
    private String candidateName;
    private String skills;
    private int experienceYears;
    private LocalDate availableFrom;
    private LocalDate availableTo;
}