package com.advertisement.domain;

import com.advertisement.dto.AdvertisementDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    private int id;
    private String advertisementText;
    private String assigned;
    private String status;
}

