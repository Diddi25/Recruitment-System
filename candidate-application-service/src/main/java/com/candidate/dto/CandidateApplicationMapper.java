package com.candidate.dto;
import com.candidate.util.NameUtils;

import com.candidate.model.CandidateApplicationModel;
import com.candidate.dao.CandidateApplicationDAO;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationRequest;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * Mapper component for converting between {@link CandidateApplicationDTO} and {@link CandidateApplicationModel}.
 * Facilitates transformations between DTOs and business logic models.
 */
@Slf4j
public class CandidateApplicationMapper {

    /**
     * Converts a CandidateApplicationRequest DTO to a CandidateApplicationModel entity.
     *
     * @param request the CandidateApplicationRequest DTO containing input data
     * @return a CandidateApplicationModel entity ready for processing
     */
    public CandidateApplicationModel toModel(CandidateApplicationRequest request) {
        String[] nameParts = NameUtils.parseFullName(request.getCandidateName());
        return CandidateApplicationModel.builder()
                .firstName(nameParts[0])  // Extract first name, then last name
                .lastName(nameParts[1])
                .skills(request.getSkills())
                .experienceYears(request.getExperienceYears())
                .availableFrom(request.getAvailableFrom())
                .availableTo(request.getAvailableTo())
                .status("unhandled")
                .build();
    }

     /**
     * Converts a CandidateApplicationDAO entity to a CandidateApplicationResponse DTO.
     *
     * @param dao the CandidateApplicationDAO entity retrieved from the database
     * @return a CandidateApplicationResponse DTO for API responses
     */
    public CandidateApplicationResponse toResponse(CandidateApplicationDAO dao) {
        return CandidateApplicationResponse.builder()
                .personId(dao.getPersonId())
                .firstName(dao.getFirstName())
                .lastName(dao.getLastName())
                .statusMessage("Application submitted successfully")
                .build();
    }
}
