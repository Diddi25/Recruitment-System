package com.advertisement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    private int id;
    private String advertisementText;
    private String assigned;
    private String status;
}

