package com.advertisement.dao;

import com.advertisement.model.Advertisement;

public class AdvertisementMapper {

    public static AdvertisementDao toDao(Advertisement advertisement) {
        return new AdvertisementDao(
                advertisement.getAdvertisementText(),
                advertisement.getAssigned(),
                advertisement.getStatus()
        );
    }

    public static Advertisement toDomain(AdvertisementDao advertisementDao) {
        return new Advertisement(
                advertisementDao.getId(),
                advertisementDao.getAdvertisementText(),
                advertisementDao.getAssigned(),
                advertisementDao.getStatus()
        );
    }
}

