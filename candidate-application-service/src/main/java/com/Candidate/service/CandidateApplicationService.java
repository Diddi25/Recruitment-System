package com.Candidate.service;

import com.Candidate.model.CandidateApplication;
import com.Candidate.dto.CandidateApplicationDto.CandidateApplicationRequest;
import com.Candidate.dto.CandidateApplicationDto.CandidateApplicationResponse;
import com.Candidate.repository.CandidateRepository;
import com.Candidate.dto.CandidateApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateApplicationService {

    private final CandidateRepository repository;
    private final CandidateApplicationMapper mapper;

    // Apply for a position (save candidate application)
    public CandidateApplicationResponse applyForPosition(CandidateApplicationRequest request) {
        CandidateApplication application = mapper.toModel(request);
        application = repository.save(application);
        return mapper.toResponse(application);
    }

    // Fetch all applications
    public List<CandidateApplicationResponse> getAllApplications() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // Fetch application by ID
    public CandidateApplicationResponse getApplicationById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
}
