package com.candidate.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.candidate.dao.CandidateApplicationDAO;
import com.candidate.model.CandidateApplicationModel;

@Repository
public interface CandidateApplicationRepository extends JpaRepository<CandidateApplicationDAO, Integer> {
}
