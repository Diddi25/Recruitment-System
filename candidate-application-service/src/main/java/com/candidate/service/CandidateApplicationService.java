package com.candidate.service;

import com.candidate.dao.CandidateApplicationDAO;
import com.candidate.dao.CandidateApplicationMapper;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.exception.DatabaseException;
import com.candidate.repository.CandidateApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Service class for handling candidate applications.
 * Provides methods for retrieving applications and applying for positions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateApplicationService {

    private final CandidateApplicationRepository candidateApplicationRepository;

    /**
     * Retrieves all candidate applications.
     *
     * @return A list of {@link CandidateApplicationDTO.CandidateApplicationResponse} objects.
     * @throws DatabaseException if fetching candidate applications fails.
     */
    public List<CandidateApplicationDTO.CandidateApplicationResponse> getAllApplications() {
        log.info("Fetching all candidate applications...");
        try {
            List<CandidateApplicationDTO.CandidateApplicationResponse> responses = candidateApplicationRepository.findAll()
                    .stream()
                    .map(CandidateApplicationMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("Fetched {} candidate applications.", responses.size());
            return responses;
        } catch (Exception e) {
            log.error("Error fetching candidate applications: {}", e.getMessage(), e);
            throw new DatabaseException("Failed to fetch candidate applications.", e);
        }
    }

    /**
     * Retrieves a candidate application by its ID.
     *
     * @param id The unique identifier of the candidate application.
     * @return A {@link CandidateApplicationDTO.CandidateApplicationResponse}
     * @throws DatabaseException if there is a database failure while retrieving the record.
     */
    public Optional<CandidateApplicationDTO.CandidateApplicationResponse> getApplicationById(Integer id) throws DatabaseException {
        log.info("Fetching candidate application with id: {}", id);
        try {
            return candidateApplicationRepository.findById(id)
                    .map(CandidateApplicationMapper::toResponse);
        } catch (DataAccessException e) {  // Catch database failures
            log.error("Database error while fetching application ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Failed to retrieve candidate application due to a database issue.", e);
        }
    }

    /**
     * Submits a new candidate application.
     * This method is transactional to ensure data integrity during the save operation.
     *
     * @param request The {@link CandidateApplicationDTO.CandidateApplicationRequest} containing application details.
     * @return A {@link CandidateApplicationDTO.CandidateApplicationResponse} containing the saved application details.
     * @throws DatabaseException if the application cannot be saved.
     */
    @Transactional
    public CandidateApplicationDTO.CandidateApplicationResponse applyForPosition(CandidateApplicationDTO.CandidateApplicationRequest request) {
        log.info("Creating a new candidate application: {}", request);
        try {
            CandidateApplicationDAO savedDao = candidateApplicationRepository.save(
                    CandidateApplicationMapper.toDao(CandidateApplicationMapper.toModel(request))
            );
            log.info("Candidate application created successfully with id: {}", savedDao.getId());
            return CandidateApplicationMapper.toResponse(savedDao);
        } catch (Exception e) {
            log.error("Error saving candidate application: {}", e.getMessage(), e);
            throw new DatabaseException("Failed to save candidate application.", e);
        }
    }
}
