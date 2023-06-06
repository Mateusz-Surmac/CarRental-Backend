package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.domain.Driver;
import com.kodilla.CarRentalBackend.domain.Dto.DriverDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DriverMapperTest {

    private DriverMapper driverMapper;

    @BeforeEach
    public void setUp() {
        driverMapper = new DriverMapper();
    }

    @Test
    public void shouldMapDriverToDriverDto() {
        // Given
        Driver driver = new Driver();
        driver.setId(1L);
        driver.setFirstName("John");
        driver.setLastName("Doe");
        driver.setCompanyEmployee(true);

        // When
        DriverDto driverDto = driverMapper.mapToDriverDto(driver);

        // Then
        assertEquals(driver.getId(), driverDto.getId());
        assertEquals(driver.getFirstName(), driverDto.getFirstName());
        assertEquals(driver.getLastName(), driverDto.getLastName());
        assertEquals(driver.isCompanyEmployee(), driverDto.isCompanyEmployee());
    }

    @Test
    public void shouldMapDriverDtoToDriver() {
        // Given
        DriverDto driverDto = new DriverDto();
        driverDto.setId(1L);
        driverDto.setFirstName("John");
        driverDto.setLastName("Doe");
        driverDto.setCompanyEmployee(true);

        // When
        Driver driver = driverMapper.mapToDriver(driverDto);

        // Then
        assertEquals(driverDto.getId(), driver.getId());
        assertEquals(driverDto.getFirstName(), driver.getFirstName());
        assertEquals(driverDto.getLastName(), driver.getLastName());
        assertEquals(driverDto.isCompanyEmployee(), driver.isCompanyEmployee());
    }

    @Test
    public void shouldMapDriverListToDriverDtoList() {
        // Given
        List<Driver> driverList = new ArrayList<>();
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirstName("John");
        driver1.setLastName("Doe");
        driver1.setCompanyEmployee(true);
        driverList.add(driver1);

        Driver driver2 = new Driver();
        driver2.setId(2L);
        driver2.setFirstName("Jane");
        driver2.setLastName("Smith");
        driver2.setCompanyEmployee(false);
        driverList.add(driver2);

        // When
        List<DriverDto> driverDtoList = driverMapper.mapToDriverDtoList(driverList);

        // Then
        assertEquals(driverList.size(), driverDtoList.size());

        for (int i = 0; i < driverList.size(); i++) {
            Driver driver = driverList.get(i);
            DriverDto driverDto = driverDtoList.get(i);

            assertEquals(driver.getId(), driverDto.getId());
            assertEquals(driver.getFirstName(), driverDto.getFirstName());
            assertEquals(driver.getLastName(), driverDto.getLastName());
            assertEquals(driver.isCompanyEmployee(), driverDto.isCompanyEmployee());
        }
    }
}

