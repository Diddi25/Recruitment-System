package com.candidate.dao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "candidate_applications")
@Data
@NoArgsConstructor
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

    public CandidateApplicationDAO(String candidateName, String skills, int experienceYears, LocalDate availableFrom, LocalDate availableTo) {
        this.candidateName = candidateName;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.status = status;
    }
}
