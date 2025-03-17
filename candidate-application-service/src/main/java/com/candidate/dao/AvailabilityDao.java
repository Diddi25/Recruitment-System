package com.candidate.dao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;


/**
 * Entity class representing availability data for a person.
 * This class maps to the "availability" table in the database.
 */

@Entity
@Table(name = "availability")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Integer availabilityId;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}