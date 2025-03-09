package com.advertisement.controller;

import com.advertisement.dto.AdvertisementDto.AdvertisementRequest;
import com.advertisement.dto.AdvertisementDto.AdvertisementResponse;
import com.advertisement.dto.AdvertisementDto.PagedAdvertisementResponse;
import com.advertisement.dto.AdvertisementMapper;
import com.advertisement.exception.AdvertisementNotFoundException;
import com.advertisement.model.Advertisement;
import com.advertisement.service.AdvertisementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementControllerTest {

    @Mock
    private AdvertisementService advertisementService;

    @Mock
    private AdvertisementMapper advertisementMapper;

    @InjectMocks
    private AdvertisementController advertisementController;

    private Advertisement advertisement1;
    private Advertisement advertisement2;
    private AdvertisementResponse advertisementResponse1;
    private AdvertisementResponse advertisementResponse2;
    private AdvertisementRequest advertisementRequest;
    private List<Advertisement> advertisementList;
    private List<AdvertisementResponse> responseList;
    private PagedAdvertisementResponse pagedResponse;

    @BeforeEach
    void setUp() {
        // Initialize test data
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

        advertisementResponse1 = AdvertisementResponse.builder()
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

        advertisementResponse2 = AdvertisementResponse.builder()
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

        advertisementRequest = AdvertisementRequest.builder()
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .build();

        advertisementList = Arrays.asList(advertisement1, advertisement2);
        responseList = Arrays.asList(advertisementResponse1, advertisementResponse2);

        pagedResponse = PagedAdvertisementResponse.builder()
                .content(responseList)
                .pageNumber(0)
                .pageSize(10)
                .totalElements(2)
                .totalPages(1)
                .last(true)
                .build();
    }

    @Test
    @DisplayName("Should return paged advertisements")
    void getAdvertisements_ShouldReturnPagedAdvertisements() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<Advertisement> page = new PageImpl<>(advertisementList, pageRequest, advertisementList.size());

        when(advertisementService.getAdvertisementsPage(any())).thenReturn(page);
        when(advertisementMapper.toPagedResponse(page)).thenReturn(pagedResponse);

        // Act
        ResponseEntity<PagedAdvertisementResponse> responseEntity = advertisementController.getAdvertisements(pageNumber, pageSize);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pagedResponse, responseEntity.getBody());
        verify(advertisementService).getAdvertisementsPage(any());
        verify(advertisementMapper).toPagedResponse(page);
    }

    @Test
    @DisplayName("Should return advertisement by ID when getAdvertisementById is called with valid ID")
    void getAdvertisementById_ShouldReturnAdvertisement_WhenValidIdProvided() {
        // Arrange
        Integer id = 1;
        when(advertisementService.getAdvertisementById(id)).thenReturn(advertisement1);
        when(advertisementMapper.toResponse(advertisement1)).thenReturn(advertisementResponse1);

        // Act
        ResponseEntity<AdvertisementResponse> responseEntity = advertisementController.getAdvertisementById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(advertisementResponse1, responseEntity.getBody());
        verify(advertisementService).getAdvertisementById(id);
        verify(advertisementMapper).toResponse(advertisement1);
    }

    @Test
    @DisplayName("Should create advertisement")
    void createAdvertisement_ShouldCreateAndReturnAdvertisement() {
        // Arrange
        Advertisement newAdvertisement = Advertisement.builder()
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .build();

        Advertisement createdAdvertisement = Advertisement.builder()
                .id(3)
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .personName("New Person")
                .personSurname("Test")
                .personEmail("new.person@example.com")
                .personNumber("54321")
                .competenceName("competenceName")
                .build();

        AdvertisementResponse createdResponse = AdvertisementResponse.builder()
                .id(3)
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .personName("New Person")
                .personSurname("Test")
                .personEmail("new.person@example.com")
                .personNumber("54321")
                .competenceName("competenceName")
                .build();

        when(advertisementMapper.toModel(advertisementRequest)).thenReturn(newAdvertisement);
        when(advertisementService.createAdvertisement(newAdvertisement)).thenReturn(createdAdvertisement);
        when(advertisementMapper.toResponse(createdAdvertisement)).thenReturn(createdResponse);

        // Act
        ResponseEntity<AdvertisementResponse> responseEntity =
                advertisementController.createAdvertisement(advertisementRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdResponse, responseEntity.getBody());
        verify(advertisementMapper).toModel(advertisementRequest);
        verify(advertisementService).createAdvertisement(newAdvertisement);
        verify(advertisementMapper).toResponse(createdAdvertisement);
    }

    @Test
    @DisplayName("Should update advertisement when updateAdvertisement is called with valid ID and request")
    void updateAdvertisement_ShouldUpdateAndReturnAdvertisement() {
        // Arrange
        Integer id = 1;
        Advertisement updatedAdvertisement = Advertisement.builder()
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .build();

        Advertisement resultAdvertisement = Advertisement.builder()
                .id(1)
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .personName("Updated Person")
                .personSurname("Test")
                .personEmail("updated.person@example.com")
                .personNumber("54321")
                .competenceName("competenceName")
                .build();

        AdvertisementResponse updatedResponse = AdvertisementResponse.builder()
                .id(1)
                .personId(103)
                .competenceId(203)
                .yearsOfExperience(new BigDecimal("2.0"))
                .status("ACTIVE")
                .personName("Updated Person")
                .personSurname("Test")
                .personEmail("updated.person@example.com")
                .personNumber("54321")
                .competenceName("competenceName")
                .build();

        when(advertisementMapper.toModel(advertisementRequest)).thenReturn(updatedAdvertisement);
        when(advertisementService.updateAdvertisement(eq(id), any(Advertisement.class))).thenReturn(resultAdvertisement);
        when(advertisementMapper.toResponse(resultAdvertisement)).thenReturn(updatedResponse);

        // Act
        ResponseEntity<AdvertisementResponse> responseEntity =
                advertisementController.updateAdvertisement(id, advertisementRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedResponse, responseEntity.getBody());
        verify(advertisementMapper).toModel(advertisementRequest);
        verify(advertisementService).updateAdvertisement(eq(id), any(Advertisement.class));
        verify(advertisementMapper).toResponse(resultAdvertisement);
    }

    @Test
    @DisplayName("Should delete advertisement when deleteAdvertisement is called with valid ID")
    void deleteAdvertisement_ShouldDeleteAdvertisement() {
        // Arrange
        Integer id = 1;
        doNothing().when(advertisementService).deleteAdvertisement(id);

        // Act
        ResponseEntity<Void> responseEntity = advertisementController.deleteAdvertisement(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(advertisementService).deleteAdvertisement(id);
    }



}