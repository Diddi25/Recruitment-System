package com.candidate.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
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
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class CandidateApplicationRequest {
        private String candidateName;
        private Integer skills;
        private BigDecimal experienceYears;
        private LocalDate availableFrom;
        private LocalDate availableTo;
        private Integer personId;
    }

    /**
     * DTO for responding to a candidate application request.
     * Contains information returned from the API.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CandidateApplicationResponse {
        private Integer personId;
        private String firstName;
        private String lastName;
        private String statusMessage;
    }
}
