package com.advertisement.repository;

import com.advertisement.dao.AdvertisementDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Advertisement data.
 *
 *
 * This interface extends {@link JpaRepository}, providing CRUD operations and query methods
 * for the {@link AdvertisementDao} entity.
 *
 */
@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementDao, Integer> {
}