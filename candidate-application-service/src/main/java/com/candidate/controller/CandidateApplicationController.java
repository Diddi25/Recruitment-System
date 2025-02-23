package com.candidate.controller;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.service.CandidateApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class CandidateApplicationController {

    private final CandidateApplicationService candidateapplicationservice;

    // Get all applications
    @GetMapping
    public ResponseEntity<List<CandidateApplicationDTO.CandidateApplicationResponse>> getAllApplications() {
        List<CandidateApplicationDTO.CandidateApplicationResponse> responses = candidateapplicationservice.getAllApplications();
        return ResponseEntity.ok(responses);
    }

    // Get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<CandidateApplicationDTO.CandidateApplicationResponse> getApplicationById(@PathVariable Integer id) {
        return candidateapplicationservice.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Apply for a position
    @PostMapping
    public ResponseEntity<CandidateApplicationDTO.CandidateApplicationResponse> applyForPosition(
            @RequestBody CandidateApplicationDTO.CandidateApplicationRequest request) {
        CandidateApplicationDTO.CandidateApplicationResponse response = candidateapplicationservice.applyForPosition(request);
        return ResponseEntity.ok(response);
    }
}