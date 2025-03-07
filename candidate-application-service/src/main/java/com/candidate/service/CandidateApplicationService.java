package com.candidate.service;

import com.candidate.dao.CandidateApplicationDAO;
import com.candidate.dao.CandidateApplicationMapper;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.repository.CandidateApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateApplicationService {

    private final CandidateApplicationRepository candidateApplicationrepository;

    // Fetch all applications
    public List<CandidateApplicationDTO.CandidateApplicationResponse> getAllApplications() {
        log.info("Fetching all candidate applications...");
        List<CandidateApplicationDTO.CandidateApplicationResponse> responses = candidateApplicationrepository.findAll()
                .stream()
                .map(CandidateApplicationMapper::toResponse)
                .collect(Collectors.toList());
        log.info("Fetched {} candidate applications.", responses.size());
        return responses;
    }

    // Fetch application by ID
    public Optional<CandidateApplicationDTO.CandidateApplicationResponse> getApplicationById(Integer id) {
        log.info("Fetching candidate application with id: {}", id);
        return candidateApplicationrepository.findById(id).map(CandidateApplicationMapper::toResponse);
    }

    // Apply for a position (save candidate application)
    public CandidateApplicationDTO.CandidateApplicationResponse applyForPosition(CandidateApplicationDTO.CandidateApplicationRequest request) {
        log.info("Creating a new candidate application: {}", request);
        CandidateApplicationDAO savedDao = candidateApplicationrepository.save(CandidateApplicationMapper.toDao(CandidateApplicationMapper.toModel(request)));
        log.info("Candidate application created successfully with id: {}", savedDao.getId());
        return CandidateApplicationMapper.toResponse(savedDao);
    }
}
