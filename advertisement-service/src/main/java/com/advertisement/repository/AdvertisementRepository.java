package com.advertisement.repository;

import com.advertisement.dao.AdvertisementDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementDao, Integer> {
}