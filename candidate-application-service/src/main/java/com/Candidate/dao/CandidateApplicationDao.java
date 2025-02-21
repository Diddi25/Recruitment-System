package com.Candidate.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidate_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateApplicationDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateName;
    private String skills;
    private int experienceYears;
    private LocalDate availableFrom;
    private LocalDate availableTo;
}
