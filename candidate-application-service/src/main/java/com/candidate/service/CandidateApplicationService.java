package com.candidate.service;

import com.candidate.dao.CandidateApplicationDAO;
import com.candidate.dao.CandidateApplicationMapper;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationRequest;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationResponse;
import com.candidate.exception.DatabaseException;
import com.candidate.exception.MappingException;
import com.candidate.exception.ServiceException;
import com.candidate.repository.CandidateApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.candidate.model.CandidateApplicationModel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling candidate applications.
 * Provides methods for applying for positions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateApplicationService {

    private final CandidateApplicationRepository candidateapplicationrepository;
    private final CandidateApplicationMapper candidateApplicationMapper;

    /**
     * Submits a new candidate application.
     *
     * @param request The {@link CandidateApplicationRequest} containing application details.
     * @return A {@link CandidateApplicationResponse} containing the saved application details.
     */
    @Transactional
    public CandidateApplicationResponse applyForPosition(CandidateApplicationRequest request) throws ServiceException {
        try {
            if (request == null) {
                throw new IllegalArgumentException("Application request cannot be null.");
            }

            log.info("Processing candidate application for: {}", request.getCandidateName());

            // Convert request DTO to model
            CandidateApplicationModel model = candidateApplicationMapper.toModel(request);

            // Convert model to DAO entity for storage
            CandidateApplicationDAO candidateDAO = candidateApplicationMapper.toDao(model);

            // Save application to database
            CandidateApplicationDAO savedDAO = candidateapplicationrepository.save(candidateDAO);
            log.info("Candidate application submitted with ID: {}", savedDAO.getPersonId());

            // Convert saved entity to response DTO
            return candidateApplicationMapper.toResponse(savedDAO);

        } catch (MappingException e) {
            log.error("Mapping error: {}", e.getMessage());
            throw new ServiceException("Failed to process application due to mapping issues.", e);
        } catch (IllegalArgumentException e) {
            log.error("Invalid request: {}", e.getMessage());
            throw new ServiceException("Invalid application data provided.", e);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new ServiceException("An unexpected error occurred while processing the application.", e);
        }
    }


    /**
     * Retrieves all candidate applications. Restricted to recruiter access.
     *
     * @return A list of {@link CandidateApplicationResponse} DTOs representing all applications.
     */
    @Transactional(readOnly = true)
    public List<CandidateApplicationDTO.CandidateApplicationResponse> getAllApplications() {
        log.info("Fetching all candidate applications (Recruiter Access Only)");

        List<CandidateApplicationDAO> applications = candidateapplicationrepository.findAll();

        return applications.stream()
                .map(candidateApplicationMapper::toResponse)
                .collect(Collectors.toList());
    }
}
