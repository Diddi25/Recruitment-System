package com.candidate.dto;
import lombok.Data;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) container class for candidate applications.
 * Defines request and response structures for API interactions.
 */
public class CandidateApplicationDTO {

    /**
     * DTO for submitting a candidate application request.
     * Used when a candidate applies for a position.
     */
    @Data
    public static class CandidateApplicationRequest {
        private String candidateName;
        private String skills;
        private int experienceYears;
        private LocalDate availableFrom;
        private LocalDate availableTo;
    }

    /**
     * DTO for responding to a candidate application request.
     * Contains information returned from the API.
     */
    @Data
    public static class CandidateApplicationResponse {
        private int id;
        private String candidateName;
        private String statusMessage;
    }

    /**
     * DTO for updating the status of an application.
     * Used for marking applications as "reviewed," "accepted," etc.
     */
    @Data
    public static class StatusUpdateRequest {
        private String status;
    }
}
