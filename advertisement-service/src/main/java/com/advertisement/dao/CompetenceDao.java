package com.advertisement.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Entity class representing a competence.
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
    private String name;
}