package com.advertisement.service;

import com.advertisement.dao.AdvertisementDao;
import com.advertisement.dao.AdvertisementMapper;
import com.advertisement.model.Advertisement;
import com.advertisement.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public List<Advertisement> getAllAdvertisements() {
        log.info("Fetching all advertisements from the database...");
        List<Advertisement> advertisements = advertisementRepository.findAll()
                .stream()
                .map(AdvertisementMapper::toDomain)
                .collect(Collectors.toList());
        log.info("Fetched {} advertisements.", advertisements.size());
        return advertisements;
    }

    public Optional<Advertisement> getAdvertisement(int id) {
        log.info("Fetching advertisement with id: {}", id);
        return advertisementRepository.findById(id)
                .map(ad -> {
                    log.info("Advertisement found: {}", ad);
                    return AdvertisementMapper.toDomain(ad);
                });
    }

    public Advertisement createAdvertisement(Advertisement advertisement) {
        log.info("Creating a new advertisement: {}", advertisement);
        AdvertisementDao savedDao = advertisementRepository.save(AdvertisementMapper.toDao(advertisement));
        log.info("Advertisement created successfully with id: {}", savedDao.getId());
        return AdvertisementMapper.toDomain(savedDao);
    }

    public Optional<Advertisement> updateAdvertisement(int id, Advertisement advertisement) {
        log.info("Updating advertisement with id: {}", id);
        return advertisementRepository.findById(id).map(existingAd -> {
            log.debug("Existing advertisement: {}", existingAd);
            existingAd.setAdvertisementText(advertisement.getAdvertisementText());
            existingAd.setAssigned(advertisement.getAssigned());
            existingAd.setStatus(advertisement.getStatus());
            AdvertisementDao updatedAd = advertisementRepository.save(existingAd);
            log.info("Advertisement updated successfully: {}", updatedAd);
            return AdvertisementMapper.toDomain(updatedAd);
        });
    }

    public boolean deleteAdvertisement(int id) {
        log.info("Deleting advertisement with id: {}", id);
        if (advertisementRepository.existsById(id)) {
            advertisementRepository.deleteById(id);
            log.info("Advertisement with id {} deleted successfully.", id);
            return true;
        }
        log.warn("Advertisement with id {} not found. Deletion failed.", id);
        return false;
    }

    public Optional<Advertisement> updateStatus(int id, String status) {
        log.info("Updating status for advertisement with id: {} to {}", id, status);
        return advertisementRepository.findById(id).map(existingAd -> {
            log.debug("Current advertisement: {}", existingAd);
            existingAd.setStatus(status);
            AdvertisementDao updatedAd = advertisementRepository.save(existingAd);
            log.info("Advertisement status updated successfully: {}", updatedAd);
            return AdvertisementMapper.toDomain(updatedAd);
        });
    }
}
