package com.Candidate.dto;

import lombok.Data;
import java.time.LocalDate;

public class CandidateApplicationDto {

    @Data
    public static class CandidateApplicationRequest {
        private String candidateName;
        private String skills;
        private int experienceYears;
        private LocalDate availableFrom;
        private LocalDate availableTo;
    }

    @Data
    public static class CandidateApplicationResponse {
        private Long id;
        private String candidateName;
        private String statusMessage;
    }

    @Data
    public static class StatusUpdateRequest {
        private String status;
    }
}
