package com.candidate.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Entity class representing a competence.
 * This class is mapped to the `competence` table in the database.
 */
@Entity
@Table(name = "competence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetenceDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competence_id")
    private Integer id;

    @Column(name = "name")
    private String skills;
}