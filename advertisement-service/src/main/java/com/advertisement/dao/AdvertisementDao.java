package com.advertisement.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "advertisements")
@Data
@NoArgsConstructor
public class AdvertisementDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String advertisementText;
    private String assigned;
    private String status;

    public AdvertisementDao(String advertisementText, String assigned, String status) {
        this.advertisementText = advertisementText;
        this.assigned = assigned;
        this.status = status;
    }
}