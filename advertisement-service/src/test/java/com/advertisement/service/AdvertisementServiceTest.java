package com.advertisement.service;

import com.advertisement.dao.AdvertisementDao;
import com.advertisement.dao.AdvertisementMapper;
import com.advertisement.exception.AdvertisementNotFoundException;
import com.advertisement.model.Advertisement;
import com.advertisement.repository.AdvertisementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private AdvertisementService advertisementService;

    private AdvertisementDao advertisementDao1;
    private AdvertisementDao advertisementDao2;
    private Advertisement advertisement1;
    private Advertisement advertisement2;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        // Initialize test data
        advertisementDao1 = new AdvertisementDao();
        advertisementDao1.setId(1);
        advertisementDao1.setPersonId(101);
        advertisementDao1.setCompetenceId(201);
        advertisementDao1.setYearsOfExperience(new BigDecimal("3.5"));
        advertisementDao1.setStatus("ACTIVE");

        advertisementDao2 = new AdvertisementDao();
        advertisementDao2.setId(2);
        advertisementDao2.setPersonId(102);
        advertisementDao2.setCompetenceId(202);
        advertisementDao2.setYearsOfExperience(new BigDecimal("5.0"));
        advertisementDao2.setStatus("ACTIVE");

        advertisement1 = Advertisement.builder()
                .id(1)
                .personId(101)
                .competenceId(201)
                .yearsOfExperience(new BigDecimal("3.5"))
                .status("ACTIVE")
                .personName("John")
                .personSurname("Doe")
                .personEmail("john.doe@example.com")
                .personNumber("12345")
                .competenceName("Java Developer")
                .build();

        advertisement2 = Advertisement.builder()
                .id(2)
                .personId(102)
                .competenceId(202)
                .yearsOfExperience(new BigDecimal("5.0"))
                .status("ACTIVE")
                .personName("Jane")
                .personSurname("Smith")
                .personEmail("jane.smith@example.com")
                .personNumber("67890")
                .competenceName("Project Manager")
                .build();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Should return paged advertisements")
    void getAdvertisementsPage_ShouldReturnPagedAdvertisements() {
        // Arrange
        List<AdvertisementDao> daos = Arrays.asList(advertisementDao1, advertisementDao2);
        Page<AdvertisementDao> daoPage = new PageImpl<>(daos, pageable, daos.size());

        try (MockedStatic<AdvertisementMapper> mockedMapper = mockStatic(AdvertisementMapper.class)) {
            when(advertisementRepository.findAllWithDetailsPaginated(pageable)).thenReturn(daoPage);
            mockedMapper.when(() -> AdvertisementMapper.toDomain(advertisementDao1)).thenReturn(advertisement1);
            mockedMapper.when(() -> AdvertisementMapper.toDomain(advertisementDao2)).thenReturn(advertisement2);

            // Act
            Page<Advertisement> result = advertisementService.getAdvertisementsPage(pageable);

            // Assert
            assertEquals(2, result.getTotalElements());
            assertEquals(2, result.getContent().size());
            assertEquals(advertisement1, result.getContent().get(0));
            assertEquals(advertisement2, result.getContent().get(1));
            verify(advertisementRepository).findAllWithDetailsPaginated(pageable);
            mockedMapper.verify(() -> AdvertisementMapper.toDomain(advertisementDao1));
            mockedMapper.verify(() -> AdvertisementMapper.toDomain(advertisementDao2));
        }
    }

    @Test
    @DisplayName("Should return advertisement by ID")
    void getAdvertisementById_ShouldReturnAdvertisement_WhenValidIdProvided() {
        // Arrange
        Integer id = 1;

        try (MockedStatic<AdvertisementMapper> mockedMapper = mockStatic(AdvertisementMapper.class)) {
            when(advertisementRepository.findById(id)).thenReturn(Optional.of(advertisementDao1));
            mockedMapper.when(() -> AdvertisementMapper.toDomain(advertisementDao1)).thenReturn(advertisement1);

            // Act
            Advertisement result = advertisementService.getAdvertisementById(id);

            // Assert
            assertEquals(advertisement1, result);
            verify(advertisementRepository).findById(id);
            mockedMapper.verify(() -> AdvertisementMapper.toDomain(advertisementDao1));
        }
    }


    @Test
    @DisplayName("Should create advertisement when createAdvertisement is called")
    void createAdvertisement_ShouldCreateAndReturnAdvertisement() {
        // Arrange
        Advertisement newAdvertisement = Advertisement.builder()
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .build();

        AdvertisementDao newDao = new AdvertisementDao();
        newDao.setPersonId(103);
        newDao.setCompetenceId(203);
        newDao.setYearsOfExperience(new BigDecimal("2.0"));
        newDao.setStatus("ACTIVE");

        AdvertisementDao savedDao = new AdvertisementDao();
        savedDao.setId(3);
        savedDao.setPersonId(103);
        savedDao.setCompetenceId(203);
        savedDao.setYearsOfExperience(new BigDecimal("2.0"));
        savedDao.setStatus("ACTIVE");

        Advertisement savedAdvertisement = Advertisement.builder()
                .id(3)
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .personName("New Person")
                .personSurname("Test")
                .personEmail("new.person@example.com")
                .personNumber("54321")
                .competenceName("Designer")
                .build();

        try (MockedStatic<AdvertisementMapper> mockedMapper = mockStatic(AdvertisementMapper.class)) {
            mockedMapper.when(() -> AdvertisementMapper.toDao(newAdvertisement)).thenReturn(newDao);
            when(advertisementRepository.save(newDao)).thenReturn(savedDao);

            // We need to mock the getAdvertisementById call that happens internally
            when(advertisementRepository.findById(3)).thenReturn(Optional.of(savedDao));
            mockedMapper.when(() -> AdvertisementMapper.toDomain(savedDao)).thenReturn(savedAdvertisement);

            // Act
            Advertisement result = advertisementService.createAdvertisement(newAdvertisement);

            // Assert
            assertEquals(savedAdvertisement, result);
            verify(advertisementRepository).save(newDao);
            verify(advertisementRepository).findById(3);
            mockedMapper.verify(() -> AdvertisementMapper.toDao(newAdvertisement));
            mockedMapper.verify(() -> AdvertisementMapper.toDomain(savedDao));
        }
    }

    @Test
    @DisplayName("Should update advertisement")
    void updateAdvertisement_ShouldUpdateAndReturnAdvertisement_WhenValidIdProvided() {
        // Arrange
        Integer id = 1;
        Advertisement updatedAdvertisement = Advertisement.builder()
                .personId(101)
                .competenceId(201)
                .yearsOfExperience(new BigDecimal("4.0"))
                .status("UPDATED")
                .build();

        AdvertisementDao updatedDao = new AdvertisementDao();
        updatedDao.setId(1);
        updatedDao.setPersonId(101);
        updatedDao.setCompetenceId(201);
        updatedDao.setYearsOfExperience(new BigDecimal("4.0"));
        updatedDao.setStatus("UPDATED");

        Advertisement resultAdvertisement = Advertisement.builder()
                .id(1)
                .personId(101)
                .competenceId(201)
                .yearsOfExperience(new BigDecimal("4.0"))
                .status("UPDATED")
                .personName("John")
                .personSurname("Doe")
                .personEmail("john.doe@example.com")
                .personNumber("12345")
                .competenceName("Java Developer")
                .build();

        try (MockedStatic<AdvertisementMapper> mockedMapper = mockStatic(AdvertisementMapper.class)) {
            when(advertisementRepository.existsById(id)).thenReturn(true);
            mockedMapper.when(() -> AdvertisementMapper.toDao(updatedAdvertisement)).thenReturn(updatedDao);
            when(advertisementRepository.save(updatedDao)).thenReturn(updatedDao);

            // We need to mock the getAdvertisementById call that happens internally
            when(advertisementRepository.findById(id)).thenReturn(Optional.of(updatedDao));
            mockedMapper.when(() -> AdvertisementMapper.toDomain(updatedDao)).thenReturn(resultAdvertisement);

            // Act
            Advertisement result = advertisementService.updateAdvertisement(id, updatedAdvertisement);

            // Assert
            assertEquals(resultAdvertisement, result);
            verify(advertisementRepository).existsById(id);
            verify(advertisementRepository).save(updatedDao);
            verify(advertisementRepository).findById(id);
            mockedMapper.verify(() -> AdvertisementMapper.toDao(updatedAdvertisement));
            mockedMapper.verify(() -> AdvertisementMapper.toDomain(updatedDao));
        }
    }

    @Test
    @DisplayName("Should delete advertisement")
    void deleteAdvertisement_ShouldDeleteAdvertisement_WhenValidIdProvided() {
        // Arrange
        Integer id = 1;
        when(advertisementRepository.existsById(id)).thenReturn(true);
        doNothing().when(advertisementRepository).deleteById(id);

        // Act
        advertisementService.deleteAdvertisement(id);

        // Assert
        verify(advertisementRepository).existsById(id);
        verify(advertisementRepository).deleteById(id);
    }
}