package com.advertisement.dao;

import com.advertisement.model.Advertisement;

/**
 * Mapper class responsible for converting between Advertisement domain objects and AdvertisementDao entities.
 */
public class AdvertisementMapper {

    /**
     * Converts an Advertisement domain object to an AdvertisementDao entity.
     *
     * @param advertisement the Advertisement domain object to convert
     * @return an AdvertisementDao entity representing the given domain object
     */
    public static AdvertisementDao toDao(Advertisement advertisement) {
        return new AdvertisementDao(
                advertisement.getAdvertisementText(),
                advertisement.getAssigned(),
                advertisement.getStatus()
        );
    }

    /**
     * Converts an AdvertisementDao entity to an Advertisement domain object.
     *
     * @param advertisementDao the AdvertisementDao entity to convert
     * @return an Advertisement domain object representing the given entity
     */
    public static Advertisement toDomain(AdvertisementDao advertisementDao) {
        return new Advertisement(
                advertisementDao.getId(),
                advertisementDao.getAdvertisementText(),
                advertisementDao.getAssigned(),
                advertisementDao.getStatus()
        );
    }
}

