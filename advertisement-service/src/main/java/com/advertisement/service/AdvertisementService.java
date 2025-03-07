package com.advertisement.service;

import com.advertisement.dao.AdvertisementDao;
import com.advertisement.dao.AdvertisementMapper;
import com.advertisement.exception.AdvertisementNotFoundException;
import com.advertisement.model.Advertisement;
import com.advertisement.repository.AdvertisementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling advertisement-related business logic.
 *
 * <p>
 * The class is annotated with {@link Service} to indicate that it is a service component
 * in the Spring framework. It uses {@link RequiredArgsConstructor} to automatically generate
 * a constructor for its dependencies and {@link Slf4j} for logging.
 * </p>
 */
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    /**
     * Retrieves all advertisements from the database.
     *
     * @return a list of all advertisements
     */
    public List<Advertisement> getAllAdvertisements() {
        log.info("Fetching all advertisements from the database...");
        List<Advertisement> advertisements = advertisementRepository.findAll()
                .stream()
                .map(AdvertisementMapper::toDomain)
                .collect(Collectors.toList());
        log.info("Fetched {} advertisements.", advertisements.size());
        return advertisements;
    }

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement
     * @return an {@link Optional} containing the advertisement if found, otherwise empty
     * @throws AdvertisementNotFoundException if no advertisement is found with the given ID
     *
     */
    public Advertisement getAdvertisement(int id) {
        log.info("Fetching advertisement with id: {}", id);
        return advertisementRepository.findById(id)
                .map(ad -> {
                    log.info("Advertisement found: {}", ad);
                    return AdvertisementMapper.toDomain(ad);
                })
                .orElseThrow(() -> new AdvertisementNotFoundException("No advertisement found with id: " + id));
    }

    /**
     * Creates a new advertisement and saves it to the database.
     *
     * @param advertisement the advertisement to create
     * @return the created advertisement
     */
    public Advertisement createAdvertisement(Advertisement advertisement) {
        log.info("Creating a new advertisement: {}", advertisement);
        AdvertisementDao savedDao = advertisementRepository.save(AdvertisementMapper.toDao(advertisement));
        log.info("Advertisement created successfully with id: {}", savedDao.getId());
        return AdvertisementMapper.toDomain(savedDao);
    }

    /**
     * Updates an existing advertisement by its ID.
     *
     * @param id the ID of the advertisement to update
     * @param advertisement the updated advertisement data
     * @return an {@link Optional} containing the updated advertisement if successful
     * @throws AdvertisementNotFoundException if no advertisement is found with the given ID
     */
    public Optional<Advertisement> updateAdvertisement(int id, Advertisement advertisement) {
        log.info("Updating advertisement with id: {}", id);
        Optional<Advertisement> existingAdOpt = advertisementRepository.findById(id)
                .map(existingAd -> {
                    log.debug("Existing advertisement: {}", existingAd);
                    existingAd.setAdvertisementText(advertisement.getAdvertisementText());
                    existingAd.setAssigned(advertisement.getAssigned());
                    existingAd.setStatus(advertisement.getStatus());
                    AdvertisementDao updatedAd = advertisementRepository.save(existingAd);
                    log.info("Advertisement updated successfully: {}", updatedAd);
                    return AdvertisementMapper.toDomain(updatedAd);
                });
        if (existingAdOpt.isEmpty()) {
            log.warn("Advertisement with id {} not found", id);
            throw new AdvertisementNotFoundException("No advertisement found with id: " + id);
        }
        return existingAdOpt;
    }

    /**
     * Deletes an advertisement by its ID.
     *
     * @param id the ID of the advertisement to delete
     * @return {@code true} if the advertisement was deleted, {@code false} if it was not found
     * @throws AdvertisementNotFoundException if no advertisement is found with the given ID
     */
    public boolean deleteAdvertisement(int id) {
        log.info("Deleting advertisement with id: {}", id);
        if (advertisementRepository.existsById(id)) {
            advertisementRepository.deleteById(id);
            log.info("Advertisement with id {} deleted successfully.", id);
            return true;
        }
        log.warn("Advertisement with id {} not found. Deletion failed.", id);
        throw new AdvertisementNotFoundException("No advertisement found with id: " + id);
    }

    /**
     * Updates the status of an advertisement by its ID.
     *
     * @param id the ID of the advertisement
     * @param status the new status to set
     * @return an {@link Optional} containing the updated advertisement if found, otherwise empty
     * @throws AdvertisementNotFoundException if no advertisement is found with the given ID
     */
    public Optional<Advertisement> updateStatus(int id, String status) {
        log.info("Updating status for advertisement with id: {} to {}", id, status);
        Optional<Advertisement> existingAdOpt = advertisementRepository.findById(id)
                .map(existingAd -> {
                    log.debug("Current advertisement: {}", existingAd);
                    existingAd.setStatus(status);
                    AdvertisementDao updatedAd = advertisementRepository.save(existingAd);
                    log.info("Advertisement status updated successfully: {}", updatedAd);
                    return AdvertisementMapper.toDomain(updatedAd);
                });
        if (existingAdOpt.isEmpty()) {
            log.warn("Advertisement with id {} not found", id);
            throw new AdvertisementNotFoundException("No advertisement found with id: " + id);
        }
        return existingAdOpt;
    }
}
