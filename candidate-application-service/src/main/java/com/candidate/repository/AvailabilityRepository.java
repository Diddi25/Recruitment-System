package com.candidate.repository;

import com.candidate.dao.AvailabilityDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing candidate applications; {@link AvailabilityDao} entities in the database.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityDao, Integer> {

}
