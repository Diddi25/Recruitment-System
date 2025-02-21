package com.Candidate.dto;

import com.Candidate.model.CandidateApplication;
import com.Candidate.dto.CandidateApplicationDto.CandidateApplicationRequest;
import com.Candidate.dto.CandidateApplicationDto.CandidateApplicationResponse;
import org.springframework.stereotype.Component;

@Component
public class CandidateApplicationMapper {

    // Convert DTO Request to Entity
    public CandidateApplication toModel(CandidateApplicationRequest request) {
        return CandidateApplication.builder()
                .candidateName(request.getCandidateName())
                .skills(request.getSkills())
                .experienceYears(request.getExperienceYears())
                .availableFrom(request.getAvailableFrom())
                .availableTo(request.getAvailableTo())
                .build();
    }

    // Convert Entity to DTO Response
    public CandidateApplicationResponse toResponse(CandidateApplication application) {
        CandidateApplicationResponse response = new CandidateApplicationResponse();
        response.setId(application.getId());
        response.setCandidateName(application.getCandidateName());
        response.setStatusMessage("Application retrieved successfully");
        return response;
    }
}
