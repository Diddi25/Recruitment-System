package com.advertisement.controller;

import com.advertisement.dto.AdvertisementDto.AdvertisementRequest;
import com.advertisement.dto.AdvertisementDto.AdvertisementResponse;
import com.advertisement.dto.AdvertisementDto.StatusUpdateRequest;
import com.advertisement.dto.AdvertisementMapper;
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

import lombok.extern.slf4j.Slf4j;

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

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisement(@PathVariable int id) {
        log.info("Fetching advertisement with id: {}", id);
        return advertisementService.getAdvertisement(id)
                .map(advertisementMapper::toResponse)
                .map(response -> {
                    log.debug("Found advertisement: {}", response);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("Advertisement with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

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
