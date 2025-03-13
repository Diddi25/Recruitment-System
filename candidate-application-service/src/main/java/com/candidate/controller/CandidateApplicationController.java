package com.candidate.controller;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.service.CandidateApplicationService;
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
     * Retrieves all candidate applications.
     *
     * @return A {@link ResponseEntity} containing a list of all candidate applications.
     */
    // Get all applications
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<CandidateApplicationDTO.CandidateApplicationResponse>> getAllApplications() {
        List<CandidateApplicationDTO.CandidateApplicationResponse> responses = candidateapplicationservice.getAllApplications();
        return ResponseEntity.ok(responses);
    }

    // Get application by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    /**
     * Retrieves a specific candidate application by its ID.
     *
     * @param id The ID of the candidate application.
     * @return A {@link ResponseEntity} containing the application details if found,
     *         or a 404 response if not found.
     */
    public ResponseEntity<CandidateApplicationDTO.CandidateApplicationResponse> getApplicationById(@PathVariable Integer id) {
        return candidateapplicationservice.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Submits an application for a position.
     *
     * @param request The application request details.
     * @return A {@link ResponseEntity} containing the response after applying.
     */
    @PostMapping("/apply")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CandidateApplicationDTO.CandidateApplicationResponse> applyForPosition(
            @RequestBody CandidateApplicationDTO.CandidateApplicationRequest request) {
        CandidateApplicationDTO.CandidateApplicationResponse response = candidateapplicationservice.applyForPosition(request);
        return ResponseEntity.ok(response);
    }

}