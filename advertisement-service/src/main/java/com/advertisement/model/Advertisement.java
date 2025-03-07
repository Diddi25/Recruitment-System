package com.advertisement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an Advertisement entity.
 * <p>
 * This class stores details of an advertisement, including its ID, text, assigned user, and status.
 * It uses Lombok annotations for automatic generation of boilerplate code.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    private int id;
    private String advertisementText;
    private String assigned;
    private String status;
}

