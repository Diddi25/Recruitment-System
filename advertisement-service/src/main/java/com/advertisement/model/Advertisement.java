package com.advertisement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents an Advertisement.
 * <p>
 * This class stores details of an advertisement, including its ID, text, assigned user, and status.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Advertisement {
    private Integer id;
    private Integer personId;
    private Integer competenceId;
    private BigDecimal yearsOfExperience;
    private String status;

    // Person information
    private String personName;
    private String personSurname;
    private String personEmail;
    private String personNumber;

    // Competence information
    private String competenceName;
}