package com.candidate.repository;

import org.springframework.stereotype.Repository;
import com.candidate.dao.CompetenceDao;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing candidate applications; {@link CompetenceDao} entities in the database.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
@Repository
public interface CompetenceRepository extends JpaRepository<CompetenceDao, Integer> {


}

