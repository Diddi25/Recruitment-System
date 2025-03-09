package com.advertisement.dto;

import com.advertisement.model.Advertisement;
import com.advertisement.dto.AdvertisementDto.AdvertisementRequest;
import com.advertisement.dto.AdvertisementDto.AdvertisementResponse;
import com.advertisement.dto.AdvertisementDto.PagedAdvertisementResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdvertisementMapper {

    /**
     * Converts an {@link AdvertisementRequest} to an {@link Advertisement} model.
     *
     * @param request the advertisement request containing input data
     * @return the mapped {@link Advertisement} entity
     */
    public Advertisement toModel(AdvertisementRequest request) {
        return Advertisement.builder()
                .personId(request.getPersonId())
                .competenceId(request.getCompetenceId())
                .yearsOfExperience(request.getYearsOfExperience())
                .status(request.getStatus())
                .build();
    }

    /**
     * Converts an {@link Advertisement} entity to an {@link AdvertisementResponse}.
     *
     * @param advertisement the advertisement entity
     * @return the mapped {@link AdvertisementResponse}
     */
    public AdvertisementResponse toResponse(Advertisement advertisement) {
        return AdvertisementResponse.builder()
                .id(advertisement.getId())
                .personId(advertisement.getPersonId())
                .competenceId(advertisement.getCompetenceId())
                .yearsOfExperience(advertisement.getYearsOfExperience())
                .status(advertisement.getStatus())
                .personName(advertisement.getPersonName())
                .personSurname(advertisement.getPersonSurname())
                .personEmail(advertisement.getPersonEmail())
                .personNumber(advertisement.getPersonNumber())
                .competenceName(advertisement.getCompetenceName())
                .build();
    }

    /**
     * Converts a list of domain models to response DTOs.
     *
     * @param advertisements the list of advertisements to convert
     * @return the list of response DTOs
     */
    public List<AdvertisementResponse> toResponseList(List<Advertisement> advertisements) {
        return advertisements.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Converts a page of domain models to a paged response DTO.
     *
     * @param page the page of advertisements
     * @return the paged response DTO
     */
    public PagedAdvertisementResponse toPagedResponse(Page<Advertisement> page) {
        List<AdvertisementResponse> content = page.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return PagedAdvertisementResponse.builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}