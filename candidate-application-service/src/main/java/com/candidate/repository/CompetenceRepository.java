package com.candidate.repository;

import com.candidate.dao.CompetenceDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetenceRepository extends JpaRepository<CompetenceDao, Integer> {
    Optional<CompetenceDao> findBySkills(String skills);
}