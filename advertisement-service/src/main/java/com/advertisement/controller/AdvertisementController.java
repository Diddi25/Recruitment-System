package com.advertisement.controller;

import com.advertisement.dto.AdvertisementDto.AdvertisementRequest;
import com.advertisement.dto.AdvertisementDto.AdvertisementResponse;
import com.advertisement.dto.AdvertisementDto.PagedAdvertisementResponse;
import com.advertisement.dto.AdvertisementMapper;
import com.advertisement.model.Advertisement;
import com.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing advertisements.
 * Provides endpoints for creating, retrieving, updating, and deleting advertisements.
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

    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    /**
     * Gets a paginated list of advertisements.
     *
     * @param pageNumber the page number (0-based)
     * @param pageSize   the page size
     * @return a paged response of advertisements
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<PagedAdvertisementResponse> getAdvertisements(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "100") int pageSize) {

        log.info("Getting advertisements page {} with size {}", pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<Advertisement> advertisementPage = advertisementService.getAdvertisementsPage(pageable);

        PagedAdvertisementResponse response = advertisementMapper.toPagedResponse(advertisementPage);
        return ResponseEntity.ok(response);
    }

    /**
     * Gets an advertisement by ID.
     *
     * @param id the advertisement ID
     * @return the advertisement response
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<AdvertisementResponse> getAdvertisementById(@PathVariable Integer id) {
        log.info("Getting advertisement with id: {}", id);
        Advertisement advertisement = advertisementService.getAdvertisementById(id);
        return ResponseEntity.ok(advertisementMapper.toResponse(advertisement));
    }

    /**
     * Retrieves all advertisements.
     *
     * @return A response entity containing a list of all advertisements.
     */
    @GetMapping("/by-person/{personId}")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<List<AdvertisementResponse>> getAdvertisementsByPersonId(@PathVariable Integer personId) {
        log.info("Getting advertisements for person with id: {}", personId);
        List<Advertisement> advertisements = advertisementService.getAdvertisementsByPersonId(personId);
        return ResponseEntity.ok(advertisementMapper.toResponseList(advertisements));
    }

    /**
     * Creates a new advertisement.
     *
     * @param request The request body containing advertisement details.
     * @return A response entity containing the created advertisement.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<AdvertisementResponse> createAdvertisement(
            @RequestBody AdvertisementRequest request) {
        log.info("Creating advertisement for person ID: {} and competence ID: {}",
                request.getPersonId(), request.getCompetenceId());

        Advertisement advertisement = advertisementMapper.toModel(request);
        Advertisement createdAdvertisement = advertisementService.createAdvertisement(advertisement);

        return new ResponseEntity<>(
                advertisementMapper.toResponse(createdAdvertisement),
                HttpStatus.CREATED
        );
    }

    /**
     * Updates an existing advertisement.
     *
     * @param id      the advertisement ID
     * @param request the updated advertisement request
     * @return the updated advertisement response
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<AdvertisementResponse> updateAdvertisement(
            @PathVariable Integer id,
            @RequestBody AdvertisementRequest request) {
        log.info("Updating advertisement with id: {}", id);

        Advertisement advertisement = advertisementMapper.toModel(request);
        Advertisement updatedAdvertisement = advertisementService.updateAdvertisement(id, advertisement);

        return ResponseEntity.ok(advertisementMapper.toResponse(updatedAdvertisement));
    }

    /**
     * Deletes an advertisement.
     *
     * @param id the advertisement ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Integer id) {
        log.info("Deleting advertisement with id: {}", id);
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles the exception when an advertisement is not found.
     *
     * @param ex the exception
     * @return error response with 404 status
     */
    @ExceptionHandler(com.advertisement.exception.AdvertisementNotFoundException.class)
    public ResponseEntity<String> handleAdvertisementNotFoundException(
            com.advertisement.exception.AdvertisementNotFoundException ex) {
        log.error("Advertisement not found: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation exceptions.
     *
     * @param ex the exception
     * @return error response with 400 status
     */
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        return new ResponseEntity<>("Invalid request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}