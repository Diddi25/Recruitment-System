package com.candidate.dto;
import lombok.Data;
import java.time.LocalDate;

public class CandidateApplicationDTO {

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
        private int id;
        private String candidateName;
        private String statusMessage;
    }

    @Data
    public static class StatusUpdateRequest {
        private String status;
    }
}
