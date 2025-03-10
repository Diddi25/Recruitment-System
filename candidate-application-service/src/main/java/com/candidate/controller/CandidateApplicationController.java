package com.candidate.controller;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.service.CandidateApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

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
    public ResponseEntity<List<CandidateApplicationDTO.CandidateApplicationResponse>> getAllApplications() {
        List<CandidateApplicationDTO.CandidateApplicationResponse> responses = candidateapplicationservice.getAllApplications();
        return ResponseEntity.ok(responses);
    }
    /**
     * Retrieves a specific candidate application by its ID.
     *
     * @param id The ID of the candidate application.
     * @return A {@link ResponseEntity} containing the application details if found,
     *         or a 404 response if not found.
     */
    @GetMapping("/{id}")      // Get application by ID
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
    public ResponseEntity<CandidateApplicationDTO.CandidateApplicationResponse> applyForPosition(
            @RequestBody CandidateApplicationDTO.CandidateApplicationRequest request) {
        CandidateApplicationDTO.CandidateApplicationResponse response = candidateapplicationservice.applyForPosition(request);
        return ResponseEntity.ok(response);
    }
}