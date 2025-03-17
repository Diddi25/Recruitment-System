package com.candidate.controller;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.service.CandidateApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing candidate applications.
 * Provides endpoints for retrieving applications and applying for positions.
 */
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class CandidateApplicationController {

    private final CandidateApplicationService candidateapplicationservice;

    /**
     * Submits an application for a position.
     * @param request The application request details.
     * @return A {@link ResponseEntity} containing the response after applying.
     */
    @PostMapping("/apply")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CandidateApplicationDTO.CandidateApplicationResponse> applyForPosition(
            @Valid @RequestBody CandidateApplicationDTO.CandidateApplicationRequest request) {
        System.out.println("Received application request: " + request);
        CandidateApplicationDTO.CandidateApplicationResponse response = candidateapplicationservice.applyForPosition(request);
        return ResponseEntity.ok(response);
    }
}