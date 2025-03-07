package com.advertisement.dto;

import com.advertisement.model.Advertisement;
import com.advertisement.dto.AdvertisementDto.AdvertisementRequest;
import com.advertisement.dto.AdvertisementDto.AdvertisementResponse;
import org.springframework.stereotype.Component;

/**
 * Component responsible for mapping between Advertisement entities, requests, and responses.
 * <p>
 * This class provides methods to convert {@link AdvertisementRequest} to {@link Advertisement},
 * and {@link Advertisement} to {@link AdvertisementResponse}.
 * </p>
 */
@Component
public class AdvertisementMapper {

    /**
     * Converts an {@link AdvertisementRequest} to an {@link Advertisement} model.
     *
     * @param request the advertisement request containing input data
     * @return the mapped {@link Advertisement} entity
     */
    public Advertisement toModel(AdvertisementRequest request) {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementText(request.getAdvertisementText());
        advertisement.setAssigned(request.getAssigned());
        advertisement.setStatus(request.getStatus() != null ? request.getStatus() : "unhandled");
        return advertisement;
    }

    /**
     * Converts an {@link Advertisement} entity to an {@link AdvertisementResponse}.
     *
     * @param advertisement the advertisement entity
     * @return the mapped {@link AdvertisementResponse}
     */
    public AdvertisementResponse toResponse(Advertisement advertisement) {
        AdvertisementResponse response = new AdvertisementResponse();
        response.setId(advertisement.getId());
        response.setAdvertisementText(advertisement.getAdvertisementText());
        response.setAssigned(advertisement.getAssigned());
        response.setStatus(advertisement.getStatus());
        return response;
    }
}