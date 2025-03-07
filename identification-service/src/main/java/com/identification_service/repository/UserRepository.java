package com.identification_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.identification_service.model.User;

/**
 * Repository interface for managing database operations for the {@link User} entity.
 * <p>
 * Inherits from {@link JpaRepository} to provide standard CRUD operations.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
