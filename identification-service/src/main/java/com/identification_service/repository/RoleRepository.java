package com.identification_service.repository;

import com.identification_service.model.Role;
import com.identification_service.model.EnumRoles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EnumRoles name);
}