package com.candidate.service;

import com.candidate.dao.CandidateApplicationDAO;
import com.candidate.dao.AvailabilityDao;
import com.candidate.dao.CompetenceProfileDao;
import com.candidate.exception.DatabaseException;
import com.candidate.mapper.CandidateApplicationMapper;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationRequest;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationResponse;
import com.candidate.repository.AvailabilityRepository;
import com.candidate.repository.CompetenceProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.candidate.model.CandidateApplicationModel;

/**
 * Service class for handling candidate applications.
 * Provides methods for applying for positions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateApplicationService {

    private final AvailabilityRepository availabilityRepository;
    private final CompetenceProfileRepository competenceProfileRepository;
    private final CandidateApplicationMapper candidateApplicationMapper;


    /**
     * Submits a new candidate application.
     */
    @Transactional
    public CandidateApplicationResponse applyForPosition(CandidateApplicationRequest request) {
        log.info("Processing candidate application for: {}", request.getCandidateName());

        if (request.getCandidateName() == null || request.getCandidateName().isEmpty()) {
            throw new DatabaseException("Application request is invalid: Candidate name is required.");
        }

        if (request.getPersonId() == null) {
            throw new DatabaseException("Application request is invalid: Person ID is required.");
        }

        CandidateApplicationModel model = candidateApplicationMapper.toModel(request);
        CandidateApplicationDAO candidateDAO = candidateApplicationMapper.toDao(model);
        CompetenceProfileDao competenceProfile = candidateApplicationMapper.competenceProfileToDao(model);
        AvailabilityDao availability = candidateApplicationMapper.availabilityToDao(model);
        availabilityRepository.save(availability);
        competenceProfileRepository.save(competenceProfile);
        return candidateApplicationMapper.toResponse(candidateDAO);
    }
}
