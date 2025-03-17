package com.candidate.service;

import com.candidate.dao.CandidateApplicationDAO;
import com.candidate.dao.AvailabilityDao;
import com.candidate.dao.CompetenceDao;
import com.candidate.dao.CompetenceProfileDao;
import com.candidate.dao.CandidateApplicationMapper;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationRequest;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationResponse;
import com.candidate.repository.CandidateApplicationRepository;
import com.candidate.repository.AvailabilityRepository;
import com.candidate.repository.CompetenceProfileRepository;
import com.candidate.repository.CompetenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final CandidateApplicationRepository candidateApplicationRepository;
    private final AvailabilityRepository availabilityRepository;
    private final CompetenceProfileRepository competenceProfileRepository;
    private final CandidateApplicationMapper candidateApplicationMapper;
    private final CompetenceRepository competenceRepository;

    /**
     * Submits a new candidate application.
     */
    // @Transactional
   /* public CandidateApplicationResponse applyForPosition(CandidateApplicationRequest request) {
        log.info("Processing candidate application for: {}", request.getCandidateName());
        CandidateApplicationModel model = candidateApplicationMapper.toModel(request);
        CandidateApplicationDAO candidateDAO = candidateApplicationMapper.toDao(model);
        candidateApplicationMapper.competenceProfileToDao(model);
        candidateApplicationMapper.availabilityToDao(model);
        CompetenceProfileDao competenceProfileDao = candidateApplicationMapper.competenceProfileToDao(model);

        competenceProfileRepository.save(competenceProfileDao);  // Spara kompetensprofilen

        AvailabilityDao availabilityDao = candidateApplicationMapper.availabilityToDao(model);

        availabilityRepository.save(availabilityDao);  // Spara tillgängligheten

        return candidateApplicationMapper.toResponse(candidateDAO);
    }
} */
    @Transactional
    public CandidateApplicationResponse applyForPosition(CandidateApplicationRequest request) {
        log.info("Processing candidate application for: {}", request.getCandidateName());
        // Convert request DTO to model
        CandidateApplicationModel model = candidateApplicationMapper.toModel(request);
        // Convert model to DAO
        CandidateApplicationDAO candidateDAO = candidateApplicationMapper.toDao(model);
        // Step 1: Save Candidate First
        CandidateApplicationDAO savedCandidate = candidateApplicationRepository.save(candidateDAO);


        CompetenceDao competence = competenceRepository.findBySkills(model.getSkills())
                .orElseGet(() -> {
                    CompetenceDao newCompetence = CompetenceDao.builder()
                            .skills(model.getSkills())
                            .build();
                    return competenceRepository.save(newCompetence);
                });
        Integer competenceId = competence.getCompetenceId();

        // Step 3: Save Competence Profile (Now it knows the correct competenceId)
        CompetenceProfileDao competenceProfileDao = candidateApplicationMapper.competenceProfileToDao(model, competenceId);
        competenceProfileDao.setPersonId(savedCandidate.getPersonId());  // Link competence to saved candidate
        competenceProfileRepository.save(competenceProfileDao);

        // Step 4: Save Availability (Now it knows the person ID)
        AvailabilityDao availabilityDao = candidateApplicationMapper.availabilityToDao(model);
        availabilityDao.setPersonId(savedCandidate.getPersonId());  // Link availability to saved candidate
        availabilityRepository.save(availabilityDao);

        log.info("Candidate application submitted successfully with ID: {}", savedCandidate.getPersonId());

        // Step 5: Return response with saved data
        return candidateApplicationMapper.toResponse(savedCandidate);
    }
}
