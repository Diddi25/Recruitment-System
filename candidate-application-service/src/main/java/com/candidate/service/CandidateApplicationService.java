package com.candidate.service;

import com.candidate.dao.CandidateApplicationDAO;
import com.candidate.dao.CandidateApplicationMapper;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationRequest;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationResponse;
import com.candidate.exception.DatabaseException;
import com.candidate.repository.CandidateApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.candidate.model.CandidateApplicationModel;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling candidate applications.
 * Provides methods for applying for positions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateApplicationService {

    private final CandidateApplicationRepository candidateApplicationRepository;
    private final CandidateApplicationMapper candidateApplicationMapper;

    /**
     * Submits a new candidate application.
     */
    @Transactional
    public CandidateApplicationResponse applyForPosition(CandidateApplicationRequest request) {
        log.info("Processing candidate application for: {}", request.getCandidateName());

        // Convert request DTO to model
        CandidateApplicationModel model = candidateApplicationMapper.toModel(request);

        // Convert model to DAO entity for storage
        CandidateApplicationDAO candidateDAO = candidateApplicationMapper.toDao(model);

        // Save application to database
        CandidateApplicationDAO savedDAO = candidateApplicationRepository.save(candidateDAO);
        log.info("Candidate application submitted with ID: {}", savedDAO.getId());

        // Convert saved entity to response DTO
        return candidateApplicationMapper.toResponse(savedDAO);
    }
    @Transactional(readOnly = true)
    public List<CandidateApplicationDTO.CandidateApplicationResponse> getAllApplications() {
        log.info("Fetching all candidate applications (Recruiter Access Only)");

        List<CandidateApplicationDAO> applications = candidateApplicationRepository.findAll();

        return applications.stream()
                .map(candidateApplicationMapper::toResponse)
                .collect(Collectors.toList());
    }

}
