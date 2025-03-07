package com.advertisement.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an advertisement.
 */
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

    /**
     * Constructs an AdvertisementDao with the specified details.
     *
     * @param advertisementText the text content of the advertisement
     * @param assigned the user or entity assigned to the advertisement
     * @param status the status of the advertisement
     */
    public AdvertisementDao(String advertisementText, String assigned, String status) {
        this.advertisementText = advertisementText;
        this.assigned = assigned;
        this.status = status;
    }
}