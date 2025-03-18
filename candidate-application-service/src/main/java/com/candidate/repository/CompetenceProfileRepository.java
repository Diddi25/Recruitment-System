package com.candidate.repository;

import com.candidate.dao.CompetenceProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing candidate applications; {@link CompetenceProfileDao} entities in the database.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
@Repository
public interface CompetenceProfileRepository extends JpaRepository<CompetenceProfileDao, Integer> {


}
