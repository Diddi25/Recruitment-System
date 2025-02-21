package com.Candidate.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "candidate_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateName;
    private String skills;
    private int experienceYears;
    private LocalDate availableFrom;
    private LocalDate availableTo;
}
