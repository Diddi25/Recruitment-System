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
        return AdvertisementDao.builder()
                .id(advertisement.getId())
                .personId(advertisement.getPersonId())
                .competenceId(advertisement.getCompetenceId())
                .yearsOfExperience(advertisement.getYearsOfExperience())
                .status(advertisement.getStatus())
                .build();
    }

    /**
     * Converts an AdvertisementDao entity to an Advertisement domain object.
     *
     * @param advertisementDao the AdvertisementDao entity to convert
     * @return an Advertisement domain object representing the given entity
     */
    public static Advertisement toDomain(AdvertisementDao advertisementDao) {
        Advertisement advertisement = Advertisement.builder()
                .id(advertisementDao.getId())
                .personId(advertisementDao.getPersonId())
                .competenceId(advertisementDao.getCompetenceId())
                .yearsOfExperience(advertisementDao.getYearsOfExperience())
                .status(advertisementDao.getStatus())
                .build();

        // Add person details if available
        if (advertisementDao.getPerson() != null) {
            PersonDao person = advertisementDao.getPerson();
            advertisement.setPersonName(person.getName());
            advertisement.setPersonSurname(person.getSurname());
            advertisement.setPersonEmail(person.getEmail());
            advertisement.setPersonNumber(person.getPersonNumber());
        }

        // Add competence details if available
        if (advertisementDao.getCompetence() != null) {
            CompetenceDao competence = advertisementDao.getCompetence();
            advertisement.setCompetenceName(competence.getName());
        }

        return advertisement;
    }
}