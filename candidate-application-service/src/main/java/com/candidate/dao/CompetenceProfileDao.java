package com.candidate.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "competence_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetenceProfileDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competence_profile_id")
    private Integer id;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "competence_id")
    private Integer competenceId;

    @Column(name = "years_of_experience")
    private BigDecimal experienceYears;

    @Column(name = "status")
    private String status;

}