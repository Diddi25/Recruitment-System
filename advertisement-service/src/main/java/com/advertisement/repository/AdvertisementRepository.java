package com.advertisement.repository;

import com.advertisement.dao.AdvertisementDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementDao, Integer> {

    /**
     * Find all competence profiles with detailed information with pagination support.
     *
     * @param pageable pagination information
     * @return page of competence profiles with person and competence information
     */
    @Query(value = "SELECT a FROM AdvertisementDao a JOIN FETCH a.person JOIN FETCH a.competence",
            countQuery = "SELECT COUNT(a) FROM AdvertisementDao a")
    Page<AdvertisementDao> findAllWithDetailsPaginated(Pageable pageable);

    /**
     * Find profiles by person ID with detailed information.
     *
     * @param personId the ID of the person
     * @return list of competence profiles
     */
    @Query("SELECT a FROM AdvertisementDao a JOIN FETCH a.person JOIN FETCH a.competence WHERE a.personId = :personId")
    List<AdvertisementDao> findByPersonIdWithDetails(@Param("personId") Integer personId);





}