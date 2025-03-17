package com.candidate.dao;
import com.candidate.dto.CandidateApplicationDTO;
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

    public CandidateApplicationModel toModel(CandidateApplicationRequest request) {
        String[] nameParts = NameUtils.parseFullName(request.getCandidateName());
        return CandidateApplicationModel.builder()
                .firstName(nameParts[0])  // Extract first name, then last name
                .lastName(nameParts[1])
                .skills(request.getSkills())
                .personId(request.getPersonId())
                .experienceYears(request.getExperienceYears())
                .availableFrom(request.getAvailableFrom())
                .availableTo(request.getAvailableTo())
                .status("unhandled") // Default status for new applications
                .build();
    }

   public CandidateApplicationDAO toDao(CandidateApplicationModel model) {
        final Integer personId = model.getPersonId();
        return CandidateApplicationDAO.builder()
                .personId(personId)
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .availability(model.getAvailableFrom() != null && model.getAvailableTo() != null ?
                        AvailabilityDao.builder()
                                .personId(personId)
                                .fromDate(model.getAvailableFrom())
                                .toDate(model.getAvailableTo())
                                .build() : null)
                .competence(model.getSkills() != null ?
                        CompetenceDao.builder()
                                .build() : null)
                .competenceProfileDao(model.getExperienceYears() != null ?
                        CompetenceProfileDao.builder()
                                .experienceYears(model.getExperienceYears())
                                .status(model.getStatus())
                                .personId(personId)
                                .build() : null)
                .build();
    }

    public CompetenceDao competenceToDao(CandidateApplicationModel model) {
        return CompetenceDao.builder()
                .id(model.getSkills())
                .build();
    }

    public CompetenceProfileDao competenceProfileToDao(CandidateApplicationModel model) {
        return  CompetenceProfileDao.builder()
                .experienceYears(model.getExperienceYears())
                .status(model.getStatus())
                .personId(model.getPersonId())
                .competenceId(model.getSkills())
                .build();
    }

    public AvailabilityDao availabilityToDao(CandidateApplicationModel model) {
        return AvailabilityDao.builder()
                .personId(model.getPersonId())
                .fromDate(model.getAvailableFrom())
                .toDate(model.getAvailableTo())
                .build();
    }

    public CandidateApplicationDTO.CandidateApplicationResponse toResponse(CandidateApplicationDAO savedDAO) {
        return CandidateApplicationDTO.CandidateApplicationResponse.builder()
                .personId(savedDAO.getPersonId())
                .firstName(savedDAO.getFirstName())
                .lastName(savedDAO.getLastName())
                .statusMessage("Application submitted successfully")
                .build();
    }
}
