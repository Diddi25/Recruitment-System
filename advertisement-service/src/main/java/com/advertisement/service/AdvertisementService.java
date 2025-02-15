package com.advertisement.service;

import com.advertisement.dao.AdvertisementDao;
import com.advertisement.domain.Advertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementDao advertisementDao;

    public List<Advertisement> getAllAdvertisements() {
        return advertisementDao.findAll();
    }

    public Optional<Advertisement> getAdvertisement(int id) {
        return advertisementDao.findById(id);
    }

    public Advertisement createAdvertisement(Advertisement advertisement) {
        return advertisementDao.save(advertisement);
    }

    public Optional<Advertisement> updateAdvertisement(int id, Advertisement advertisement) {
        return advertisementDao.update(id, advertisement);
    }

    public boolean deleteAdvertisement(int id) {
        return advertisementDao.delete(id);
    }

    public Optional<Advertisement> updateStatus(int id, String status) {
        return advertisementDao.updateStatus(id, status);
    }
}