package com.advertisement.dto;

import com.advertisement.domain.Advertisement;
import com.advertisement.dto.AdvertisementDto.AdvertisementRequest;
import com.advertisement.dto.AdvertisementDto.AdvertisementResponse;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementMapper {

    public Advertisement toEntity(AdvertisementRequest request) {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementText(request.getAdvertisementText());
        advertisement.setAssigned(request.getAssigned());
        advertisement.setStatus(request.getStatus() != null ? request.getStatus() : "unhandled");
        return advertisement;
    }

    public AdvertisementResponse toResponse(Advertisement advertisement) {
        AdvertisementResponse response = new AdvertisementResponse();
        response.setId(advertisement.getId());
        response.setAdvertisementText(advertisement.getAdvertisementText());
        response.setAssigned(advertisement.getAssigned());
        response.setStatus(advertisement.getStatus());
        return response;
    }
}