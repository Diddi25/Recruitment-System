package com.advertisement.dao;

import com.advertisement.domain.Advertisement;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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