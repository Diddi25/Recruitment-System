package com.Candidate.repository;


import com.Candidate.model.CandidateApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidateRepository extends JpaRepository<CandidateApplication, Long> {
}
