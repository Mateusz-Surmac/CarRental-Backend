package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.domain.Driver;
import com.kodilla.CarRentalBackend.domain.Dto.DriverDto;
import com.kodilla.CarRentalBackend.exceptions.DriverNotFoundException;
import com.kodilla.CarRentalBackend.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DriverDbServiceTest {

    private DriverDbService driverDbService;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driverDbService = new DriverDbService(driverRepository);
    }

    @Test
    void shouldReturnDriverList() {
        // Given
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver());
        drivers.add(new Driver());
        when(driverRepository.findAll()).thenReturn(drivers);

        // When
        List<Driver> result = driverDbService.getDriverList();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnDriverById() throws DriverNotFoundException {
        // Given
        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        // When
        Driver result = driverDbService.getDriverById(driverId);

        // Then
        assertEquals(driverId, result.getId());
    }

    @Test
    void shouldReturnEmployeeDriversList() {
        // Given
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver());
        drivers.add(new Driver());
        drivers.get(0).setCompanyEmployee(true);
        when(driverRepository.findAll()).thenReturn(drivers);

        // When
        List<Driver> result = driverDbService.getEmployeeDriversList();

        // Then
        assertEquals(1, result.size());
        assertTrue(result.get(0).isCompanyEmployee());
    }

    @Test
    void shouldSaveDriver() {
        // Given
        Driver driver = new Driver();
        when(driverRepository.save(driver)).thenReturn(driver);

        // When
        Driver result = driverDbService.saveDriver(driver);

        // Then
        assertEquals(driver, result);
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void shouldUpdateDriver() throws DriverNotFoundException {
        // Given
        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);
        DriverDto driverDto = new DriverDto();
        driverDto.setFirstName("John");
        driverDto.setLastName("Doe");
        driverDto.setCompanyEmployee(true);
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(driver);

        // When
        Driver result = driverDbService.updateDriver(driverId, driverDto);

        // Then
        assertEquals(driverId, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertTrue(result.isCompanyEmployee());
    }
}

