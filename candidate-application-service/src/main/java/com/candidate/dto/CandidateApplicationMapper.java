package com.candidate.dto;
import com.candidate.model.CandidateApplicationModel;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationRequest;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationResponse;
import org.springframework.stereotype.Component;
import com.candidate.exception.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mapper component for converting between {@link CandidateApplicationDTO} and {@link CandidateApplicationModel}.
 * Facilitates transformations between DTOs and business logic models.
 */
@Component
public class CandidateApplicationMapper {

    private static final Logger log = LoggerFactory.getLogger(CandidateApplicationMapper.class);

    /**
     * Converts a {@link CandidateApplicationRequest} DTO into a {@link CandidateApplicationModel}.
     *
     * @param request The DTO containing candidate application data.
     * @return A {@link CandidateApplicationModel} representing the application in the business logic layer.
     * @throws MappingException If the request is null or contains invalid data.
     */
    public CandidateApplicationModel toModel(CandidateApplicationRequest request) throws MappingException {
        if (request == null) {
            log.error("Mapping failure: CandidateApplicationRequest is null.");
            throw new MappingException("Cannot map null CandidateApplicationRequest to CandidateApplicationModel.");
        }

        try {
            return CandidateApplicationModel.builder()
                    .candidateName(request.getCandidateName())
                    .skills(request.getSkills())
                    .experienceYears(request.getExperienceYears())
                    .availableFrom(request.getAvailableFrom())
                    .availableTo(request.getAvailableTo())
                    .build();
        } catch (Exception e) {
            log.error("Error mapping CandidateApplicationRequest to CandidateApplicationModel: {}", e.getMessage(), e);
            throw new MappingException("Invalid data format during request-to-model mapping.");
        }
    }

    /**
     * Converts a {@link CandidateApplicationModel} into a {@link CandidateApplicationResponse} DTO.
     * Used for sending application data back in API responses.
     *
     * @param application The business logic model of the application.
     * @return A {@link CandidateApplicationResponse} containing application details and status.
     * @throws MappingException If the application model is null or contains invalid data.
     */
    public CandidateApplicationResponse toResponse(CandidateApplicationModel application) throws MappingException {
        if (application == null) {
            log.error("Mapping failure: CandidateApplicationModel is null.");
            throw new MappingException("Cannot map null CandidateApplicationModel to CandidateApplicationResponse.");
        }

        try {
            CandidateApplicationResponse response = new CandidateApplicationResponse();
            response.setId(application.getId());
            response.setCandidateName(application.getCandidateName());
            response.setStatusMessage("Application retrieved successfully");
            return response;
        } catch (Exception e) {
            log.error("Error mapping CandidateApplicationModel to CandidateApplicationResponse: {}", e.getMessage(), e);
            throw new MappingException("Invalid data format during model-to-response mapping.");
        }
    }
}