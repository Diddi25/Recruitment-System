package com.candidate.dao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;



/**
 * Entity representing a candidate's job application stored in the database.
 * This class is mapped to the `candidate_applications` table.
 */
@Entity
@Table(name = "candidate_applications3")
@Data
@NoArgsConstructor
@Slf4j
public class CandidateApplicationDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String candidateName;
    private String skills;
    private int experienceYears;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private String status;

    /**
     * Constructs a new CandidateApplicationDAO with the provided details.
     * The initial status is set to "unhandled".
     *
     * @param candidateName The full name of the candidate.
     * @param skills The candidate's skills.
     * @param experienceYears The number of years of work experience.
     * @param availableFrom The start date of the candidate's availability.
     * @param availableTo The end date of the candidate's availability.
     * @throws IllegalArgumentException If any required parameter is invalid.
     */
    public CandidateApplicationDAO(String candidateName, String skills, int experienceYears, LocalDate availableFrom, LocalDate availableTo) throws IllegalArgumentException {
        if (candidateName == null || candidateName.trim().isEmpty()) {
            log.error("Candidate name is invalid: {}", candidateName);
            throw new IllegalArgumentException("Candidate name cannot be null or empty.");
        }
        if (skills == null || skills.trim().isEmpty()) {
            log.error("Skills field is invalid: {}", skills);
            throw new IllegalArgumentException("Skills cannot be null or empty.");
        }
        if (experienceYears < 0) {
            log.error("Experience years cannot be negative: {}", experienceYears);
            throw new IllegalArgumentException("Experience years cannot be negative.");
        }
        if (availableFrom == null || availableTo == null || availableFrom.isAfter(availableTo)) {
            log.error("Invalid availability dates: availableFrom={}, availableTo={}", availableFrom, availableTo);
            throw new IllegalArgumentException("Invalid availability dates. 'availableFrom' must be before 'availableTo'.");
        }

        this.candidateName = candidateName;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.status = "unhandled";
    }
}
