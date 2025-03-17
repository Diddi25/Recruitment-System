package com.candidate.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.candidate.dao.CandidateApplicationDAO;


/**
 * Repository interface for managing candidate applications.
 * Provides CRUD operations for {@link CandidateApplicationDAO} entities in the database.
 * Extends {@link JpaRepository} to leverage Spring Data JPA functionalities.
 */
@Repository
public interface CandidateApplicationRepository extends JpaRepository<CandidateApplicationDAO, Integer> {
}

