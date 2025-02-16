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

@RestController
@RequestMapping("/api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final AdvertisementMapper advertisementMapper;

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Test successful from Advertisement Service!");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementResponse>> getAllAdvertisements() {
        List<AdvertisementResponse> responses = advertisementService.getAllAdvertisements()
                .stream()
                .map(advertisementMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisement(@PathVariable int id) {
        return advertisementService.getAdvertisement(id)
                .map(advertisementMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdvertisementResponse> createAdvertisement(@RequestBody AdvertisementRequest request) {
        return ResponseEntity.ok(
                advertisementMapper.toResponse(
                        advertisementService.createAdvertisement(
                                advertisementMapper.toModel(request)
                        )
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> updateAdvertisement(
            @PathVariable int id,
            @RequestBody AdvertisementRequest request) {
        return advertisementService.updateAdvertisement(id, advertisementMapper.toModel(request))
                .map(advertisementMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable int id) {
        return advertisementService.deleteAdvertisement(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AdvertisementResponse> updateStatus(
            @PathVariable int id,
            @RequestBody StatusUpdateRequest statusRequest) {
        return advertisementService.updateStatus(id, statusRequest.getStatus())
                .map(advertisementMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}