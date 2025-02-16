package com.identification_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('RECRUITER')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/rec")
    @PreAuthorize("hasRole('RECRUITER')")
    public String moderatorAccess() {
        return "RECRUITER Board.";
    }

}
