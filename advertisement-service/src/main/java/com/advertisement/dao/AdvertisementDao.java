package com.advertisement.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;

/**
 * Entity class representing an advertisement (competence_profile).
 */
@Entity
@Table(name = "competence_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competence_profile_id")
    private Integer id;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "competence_id")
    private Integer competenceId;

    @Column(name = "years_of_experience")
    private BigDecimal yearsOfExperience;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private PersonDao person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "competence_id", insertable = false, updatable = false)
    private CompetenceDao competence;
}