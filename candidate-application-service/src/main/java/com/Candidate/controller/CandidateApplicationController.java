package com.Candidate.controller;

import com.Candidate.dto.CandidateApplicationDto.CandidateApplicationRequest;
import com.Candidate.dto.CandidateApplicationDto.CandidateApplicationResponse;
import com.Candidate.service.CandidateApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class CandidateApplicationController {

    private final CandidateApplicationService candidateapplicationservice;

    // Apply for a position
    @PostMapping
    public ResponseEntity<CandidateApplicationResponse> applyForPosition(@RequestBody CandidateApplicationRequest request) {
        CandidateApplicationResponse response = candidateapplicationservice.applyForPosition(request);
        URI location = URI.create("/api/applications/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    // Get all applications
    @GetMapping
    public ResponseEntity<List<CandidateApplicationResponse>> getAllApplications() {
        List<CandidateApplicationResponse> responses = candidateapplicationservice.getAllApplications();
        return ResponseEntity.ok(responses);
    }

    // Get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<CandidateApplicationResponse> getApplicationById(@PathVariable Long id) {
        CandidateApplicationResponse response = candidateapplicationservice.getApplicationById(id);
        return ResponseEntity.ok(response);
    }
}
