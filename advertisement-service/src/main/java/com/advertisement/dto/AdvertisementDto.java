package com.advertisement.dto;

import lombok.Data;

/**
 * Data Transfer Object container for Advertisement-related requests and responses.
 */
 public class AdvertisementDto {

    /**
     * Holds the necessary data for submitting an advertisement request.
     */
    @Data
    public static class AdvertisementRequest {
        private String advertisementText;
        private String assigned;
        private String status;
    }

    /**
     * DTO for returning advertisement details in responses.
     */
    @Data
    public static class AdvertisementResponse {
        private int id;
        private String advertisementText;
        private String assigned;
        private String status;
    }

    /**
     * DTO for updating the status of an advertisement.
     */
    @Data
    public static class StatusUpdateRequest {
        private String status;
    }
}

