package com.advertisement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object container for Advertisement-related requests and responses.
 */
public class AdvertisementDto {

    /**
     * Request DTO for creating/updating an advertisement.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdvertisementRequest {
        private Integer personId;
        private Integer competenceId;
        private BigDecimal yearsOfExperience;
        private String status;
    }

    /**
     * Response DTO for advertisement details.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdvertisementResponse {
        private Integer id;
        private Integer personId;
        private Integer competenceId;
        private BigDecimal yearsOfExperience;
        private String status;

        // Person details
        private String personName;
        private String personSurname;
        private String personEmail;
        private String personNumber;

        // Competence details
        private String competenceName;
    }

    /**
     * Response DTO for paginated advertisement results.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PagedAdvertisementResponse {
        private List<AdvertisementResponse> content;
        private int pageNumber;
        private int pageSize;
        private long totalElements;
        private int totalPages;
        private boolean last;
    }
}