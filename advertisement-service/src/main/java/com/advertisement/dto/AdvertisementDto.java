package com.advertisement.dto;

import lombok.Data;

public class AdvertisementDto {

    @Data
    public static class AdvertisementRequest {
        private String advertisementText;
        private String assigned;
        private String status;
    }

    @Data
    public static class AdvertisementResponse {
        private int id;
        private String advertisementText;
        private String assigned;
        private String status;
    }

    @Data
    public static class StatusUpdateRequest {
        private String status;
    }
}

