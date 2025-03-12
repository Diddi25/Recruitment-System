package com.advertisement.service;

import com.advertisement.dao.AdvertisementDao;
import com.advertisement.dao.AdvertisementMapper;
import com.advertisement.exception.AdvertisementNotFoundException;
import com.advertisement.model.Advertisement;
import com.advertisement.repository.AdvertisementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;


    /**
     * Retrieves a paginated list of advertisements.
     *
     * @param pageable the pagination information
     * @return a page of advertisements
     */
    public Page<Advertisement> getAdvertisementsPage(Pageable pageable) {
        log.info("Fetching advertisements page {} with size {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<AdvertisementDao> advertisementPage = advertisementRepository.findAllWithDetailsPaginated(pageable);

        List<Advertisement> advertisementList = advertisementPage.getContent().stream()
                .map(AdvertisementMapper::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(
                advertisementList,
                pageable,
                advertisementPage.getTotalElements()
        );
    }

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement to retrieve
     * @return the advertisement with the given ID
     * @throws AdvertisementNotFoundException if no advertisement with the given ID exists
     */
    public Advertisement getAdvertisementById(Integer id) {
        log.info("Fetching advertisement with id: {}", id);
        Optional<AdvertisementDao> advertisementDao = advertisementRepository.findById(id);
        return advertisementDao
                .map(AdvertisementMapper::toDomain)
                .orElseThrow(() -> new AdvertisementNotFoundException("Advertisement not found with id: " + id));
    }

    /**
     * Retrieves advertisements by person ID.
     *
     * @param personId the ID of the person
     * @return a list of advertisements for the given person
     */
    public List<Advertisement> getAdvertisementsByPersonId(Integer personId) {
        log.info("Fetching advertisements for person with id: {}", personId);
        List<AdvertisementDao> advertisements = advertisementRepository.findByPersonIdWithDetails(personId);
        return advertisements.stream()
                .map(AdvertisementMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new advertisement.
     *
     * @param advertisement the advertisement to create
     * @return the created advertisement
     */
    public Advertisement createAdvertisement(Advertisement advertisement) {
        log.info("Creating new advertisement for person ID: {} and competence ID: {}",
                advertisement.getPersonId(), advertisement.getCompetenceId());
        AdvertisementDao advertisementDao = AdvertisementMapper.toDao(advertisement);
        AdvertisementDao savedDao = advertisementRepository.save(advertisementDao);
        return getAdvertisementById(savedDao.getId()); // Fetch with all details
    }

    /**
     * Updates an existing advertisement.
     *
     * @param id the ID of the advertisement to update
     * @param advertisement the updated advertisement data
     * @return the updated advertisement
     * @throws AdvertisementNotFoundException if no advertisement with the given ID exists
     */
    public Advertisement updateAdvertisement(Integer id, Advertisement advertisement) {
        log.info("Updating advertisement with id: {}", id);
        if (!advertisementRepository.existsById(id)) {
            throw new AdvertisementNotFoundException("Advertisement not found with id: " + id);
        }

        advertisement.setId(id);
        AdvertisementDao advertisementDao = AdvertisementMapper.toDao(advertisement);
        advertisementRepository.save(advertisementDao);

        return getAdvertisementById(id); // Fetch with all details
    }

    /**
     * Deletes an advertisement by its ID.
     *
     * @param id the ID of the advertisement to delete
     * @throws AdvertisementNotFoundException if no advertisement with the given ID exists
     */
    public void deleteAdvertisement(Integer id) {
        log.info("Deleting advertisement with id: {}", id);
        if (!advertisementRepository.existsById(id)) {
            throw new AdvertisementNotFoundException("Advertisement not found with id: " + id);
        }
        advertisementRepository.deleteById(id);
    }

}