package com.identification_service.repository;

import com.identification_service.model.Role;
import com.identification_service.model.EnumRoles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing database operations for the {@link Role} entity.
 * <p>
 * Inherits from {@link JpaRepository} to provide standard CRUD operations.
 * </p>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EnumRoles name);
}