package com.candidate.dao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;



/**
 * Entity representing a candidate's job application stored in the database.
 * This class is mapped to the `candidate_applications` table.
 */

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@Slf4j
@AllArgsConstructor
@Builder
public class CandidateApplicationDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer id;

    @Column(name = "name")
    private String firstName;

    @Column(name = "surname")
    private String lastName;

    @Column(name = "person_number")
    private String personNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "competence_profile_id")
    private CompetenceProfileDao competenceProfileDao;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "competence_id")
    private CompetenceDao competence;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "availability_id")
    private AvailabilityDao availability;

}