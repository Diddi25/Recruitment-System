package com.advertisement.controller;

import com.advertisement.dto.AdvertisementDto.AdvertisementRequest;
import com.advertisement.dto.AdvertisementDto.AdvertisementResponse;
import com.advertisement.dto.AdvertisementDto.StatusUpdateRequest;
import com.advertisement.dto.AdvertisementMapper;
import com.advertisement.model.Advertisement;
import com.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing advertisements.
 * Provides endpoints for creating, retrieving, updating, and deleting advertisements.
 *
 * It is annotated with {@link RestController} to denote that it is a Spring MVC
 * controller that handles HTTP requests, and {@link RequestMapping} is used to
 * define the base URL for the advertisement-related routes.
 *
 * <p>The class uses the {@link Slf4j} annotation to enable logging
 *  functionality, which is used throughout the class to log actions performed on
 *  the advertisements.</p>
 *
 *  <p>The {@link RequiredArgsConstructor} annotation generates a constructor
 *  for required fields.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final AdvertisementMapper advertisementMapper;

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testEndpoint() {
        log.info("Test endpoint hit");
        Map<String, String> response = new HashMap<>();
        response.put("message", "Test successful from Advertisement Service!");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all advertisements.
     *
     * @return A response entity containing a list of all advertisements.
     */
    @GetMapping("/all")
    public ResponseEntity<List<AdvertisementResponse>> getAllAdvertisements() {
        log.info("Fetching all advertisements");
        List<AdvertisementResponse> responses = advertisementService.getAllAdvertisements()
                .stream()
                .map(advertisementMapper::toResponse)
                .collect(Collectors.toList());
        log.debug("Found {} advertisements", responses.size());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves a specific advertisement by its ID.
     *
     * @param id The ID of the advertisement.
     * @return A response entity containing the advertisement if found, otherwise 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisement(@PathVariable int id) {
        log.info("Fetching advertisement with id: {}", id);

        Advertisement advertisement = advertisementService.getAdvertisement(id); // Kan kasta AdvertisementNotFoundException
        AdvertisementResponse response = advertisementMapper.toResponse(advertisement);

        log.debug("Found advertisement: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new advertisement.
     *
     * @param request The request body containing advertisement details.
     * @return A response entity containing the created advertisement.
     */
    @PostMapping("/create")
    public ResponseEntity<AdvertisementResponse> createAdvertisement(@RequestBody AdvertisementRequest request) {
        log.info("Creating a new advertisement: {}", request);
        AdvertisementResponse response = advertisementMapper.toResponse(
                advertisementService.createAdvertisement(
                        advertisementMapper.toModel(request)
                )
        );
        log.debug("Created advertisement: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing advertisement.
     *
     * @param id The ID of the advertisement to update.
     * @param request The request body containing updated details.
     * @return A response entity containing the updated advertisement if found, otherwise 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> updateAdvertisement(
            @PathVariable int id,
            @RequestBody AdvertisementRequest request) {
        log.info("Updating advertisement with id: {}", id);
        return advertisementService.updateAdvertisement(id, advertisementMapper.toModel(request))
                .map(advertisementMapper::toResponse)
                .map(response -> {
                    log.debug("Updated advertisement: {}", response);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("Advertisement with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Deletes an advertisement by ID.
     *
     * @param id The ID of the advertisement to delete.
     * @return A response entity with HTTP status 200 if deleted, otherwise 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable int id) {
        log.info("Deleting advertisement with id: {}", id);
        boolean deleted = advertisementService.deleteAdvertisement(id);
        if (deleted) {
            log.info("Successfully deleted advertisement with id: {}", id);
            return ResponseEntity.ok().build();
        } else {
            log.warn("Advertisement with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates the status of an advertisement.
     *
     * @param id The ID of the advertisement.
     * @param statusRequest The request body containing the new status.
     * @return A response entity containing the updated advertisement if found, otherwise 404 Not Found.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<AdvertisementResponse> updateStatus(
            @PathVariable int id,
            @RequestBody StatusUpdateRequest statusRequest) {
        log.info("Updating status of advertisement with id: {} to {}", id, statusRequest.getStatus());
        return advertisementService.updateStatus(id, statusRequest.getStatus())
                .map(advertisementMapper::toResponse)
                .map(response -> {
                    log.debug("Updated status: {}", response);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("Advertisement with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }
}