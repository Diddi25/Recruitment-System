package com.candidate.dao;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.exception.MappingException;
import com.candidate.model.CandidateApplicationModel;
import com.candidate.util.NameUtils;
import lombok.extern.slf4j.Slf4j;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationRequest;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between CandidateApplicationModel domain objects
 * and CandidateApplicationDAO entities.
 */
@Slf4j
@Component
public class CandidateApplicationMapper {

    /**
     * Converts a CandidateApplicationRequest DTO to a CandidateApplicationModel.
     *
     * @param request The request DTO containing candidate details.
     * @return The converted CandidateApplicationModel instance.
     * @throws MappingException if the request data is invalid or incomplete.
     */
    public CandidateApplicationModel toModel(CandidateApplicationRequest request) {
        try {
            if (request.getCandidateName() == null || request.getCandidateName().isEmpty()) {
                throw new MappingException("Candidate name cannot be null or empty.");
            }

            String[] nameParts = NameUtils.parseFullName(request.getCandidateName());

            return CandidateApplicationModel.builder()
                    .firstName(nameParts.length > 0 ? nameParts[0] : "Unknown")
                    .lastName(nameParts.length > 1 ? nameParts[1] : "Unknown")
                    .skills(request.getSkills())
                    .experienceYears(request.getExperienceYears())
                    .availableFrom(request.getAvailableFrom())
                    .availableTo(request.getAvailableTo())
                    .personId(Long.parseLong(request.getPersonId()))
                    .status("unhandled") // Default status for new applications
                    .build();
        } catch (Exception e) {
            log.error("Error mapping CandidateApplicationRequest to CandidateApplicationModel: {}", e.getMessage());
            throw new MappingException("Failed to map candidate application request.", e);
        }
    }

    /**
     * Converts a CandidateApplicationModel to a CandidateApplicationDAO entity.
     * @param model The CandidateApplicationModel instance.
     * @return The converted CandidateApplicationDAO instance.
     * @throws MappingException if the model data is invalid or incomplete.
     */
    public CandidateApplicationDAO toDao(CandidateApplicationModel model) {
        try {
            if (model == null) {
                throw new MappingException("CandidateApplicationModel cannot be null.");
            }

            return CandidateApplicationDAO.builder()
                    .id(Long.valueOf(model.getId()))
                    .firstName(model.getFirstName())
                    .lastName(model.getLastName())
                    .availability(model.getAvailableFrom() != null && model.getAvailableTo() != null ?
                            AvailabilityDao.builder()
                                    .fromDate(model.getAvailableFrom())
                                    .toDate(model.getAvailableTo())
                                    .build() : null)
                    .competence(model.getSkills() != null ?
                            CompetenceDao.builder()
                                    .skills(model.getSkills())
                                    .build() : null)
                    .competenceProfileDao(model.getExperienceYears() != null ?
                            CompetenceProfileDao.builder()
                                    .experienceYears(model.getExperienceYears())
                                    .status(model.getStatus())
                                    .build() : null)
                    .build();
        } catch (Exception e) {
            log.error("Error mapping CandidateApplicationModel to CandidateApplicationDAO: {}", e.getMessage());
            throw new MappingException("Failed to map candidate application model.", e);
        }
    }
    /**
     * Converts a CandidateApplicationDAO entity to a CandidateApplicationResponse DTO.
     *
     * @param savedDAO The saved CandidateApplicationDAO entity.
     * @return The corresponding CandidateApplicationResponse DTO.
     * @throws MappingException if the saved entity data is invalid or incomplete.
     */
    public CandidateApplicationDTO.CandidateApplicationResponse toResponse(CandidateApplicationDAO savedDAO) {
        try {
            if (savedDAO == null) {
                throw new MappingException("CandidateApplicationDAO cannot be null.");
            }

            return CandidateApplicationDTO.CandidateApplicationResponse.builder()
                    .id(Math.toIntExact(savedDAO.getId())) // Long -> Integer
                    .firstName(savedDAO.getFirstName())
                    .lastName(savedDAO.getLastName())
                    .statusMessage("Application submitted successfully. Status: " +
                            (savedDAO.getCompetenceProfileDao() != null ?
                                    savedDAO.getCompetenceProfileDao().getStatus() : "unhandled"))
                    .build();
        } catch (Exception e) {
            log.error("Error mapping CandidateApplicationDAO to CandidateApplicationResponse: {}", e.getMessage());
            throw new MappingException("Failed to map candidate application entity to response.", e);
        }
    }
}
