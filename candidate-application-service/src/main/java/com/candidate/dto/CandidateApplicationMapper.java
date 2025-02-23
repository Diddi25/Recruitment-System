package com.candidate.dto;
import com.candidate.model.CandidateApplicationModel;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationRequest;
import com.candidate.dto.CandidateApplicationDTO.CandidateApplicationResponse;
import org.springframework.stereotype.Component;

@Component
public class CandidateApplicationMapper {

    // Convert DTO Request to Entity
    public CandidateApplicationModel toModel(CandidateApplicationRequest request) {
        return CandidateApplicationModel.builder()
                .candidateName(request.getCandidateName())
                .skills(request.getSkills())
                .experienceYears(request.getExperienceYears())
                .availableFrom(request.getAvailableFrom())
                .availableTo(request.getAvailableTo())
                .build();
    }

    // Convert Entity to DTO Response
    public CandidateApplicationResponse toResponse(CandidateApplicationModel application) {
        CandidateApplicationResponse response = new CandidateApplicationResponse();
        response.setId(application.getId());
        response.setCandidateName(application.getCandidateName());
        response.setStatusMessage("Application retrieved successfully");
        return response;
    }
}
