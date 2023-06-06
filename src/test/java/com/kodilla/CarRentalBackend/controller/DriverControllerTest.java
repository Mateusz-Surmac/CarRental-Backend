package com.kodilla.CarRentalBackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.CarRentalBackend.domain.Driver;
import com.kodilla.CarRentalBackend.domain.Dto.DriverDto;
import com.kodilla.CarRentalBackend.mapper.DriverMapper;
import com.kodilla.CarRentalBackend.service.DriverDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverDbService driverDbService;

    @MockBean
    private DriverMapper driverMapper;

    @InjectMocks
    private DriverController driverController;

    @Test
    public void getDriverListTest() throws Exception {
        // Given
        Driver driver1 = new Driver(1L, "Test - firstName", "Test - lastName", false);
        Driver driver2 = new Driver(2L,  "Test2 - firstName", "Test2 - lastName", false);
        List<Driver> drivers = Arrays.asList(driver1, driver2);

        DriverDto driverDto1 = new DriverDto(1L, "Test - firstName", "Test - lastName", false);
        DriverDto driverDto2 = new DriverDto(2L, "Test2 - firstName", "Test2 - lastName", false);
        List<DriverDto> driverDtos = Arrays.asList(driverDto1, driverDto2);

        Mockito.when(driverDbService.getDriverList()).thenReturn(drivers);
        Mockito.when(driverMapper.mapToDriverDtoList(drivers)).thenReturn(driverDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/driver")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Test - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Test2 - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Test2 - lastName"));
    }

    @Test
    public void getDriverTest() throws Exception {
        // Given
        Long driverId = 1L;
        Driver driver = new Driver("Test - firstName", "Test - lastName", false);
        driver.setId(driverId);
        DriverDto driverDto = new DriverDto(driverId, "Test - firstName", "Test - lastName", false);

        Mockito.when(driverDbService.getDriverById(driverId)).thenReturn(driver);
        Mockito.when(driverMapper.mapToDriverDto(driver)).thenReturn(driverDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/driver/{driverId}", driverId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(driverId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Test - lastName"));
    }

    @Test
    public void getEmployeeDriverListTest() throws Exception {
        // Given
        Driver driver1 = new Driver(1L,  "Test - firstName", "Test - lastName", false);
        Driver driver2 = new Driver(2L, "Test2 - firstName", "Test2 - lastName", false);
        List<Driver> drivers = Arrays.asList(driver1, driver2);

        DriverDto driverDto1 = new DriverDto(1L, "Test - firstName", "Test - lastName", false);
        DriverDto driverDto2 = new DriverDto(2L, "Test2 - firstName", "Test2 - lastName", false);
        List<DriverDto> driverDtos = Arrays.asList(driverDto1, driverDto2);

        Mockito.when(driverDbService.getEmployeeDriversList()).thenReturn(drivers);
        Mockito.when(driverMapper.mapToDriverDtoList(drivers)).thenReturn(driverDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/driver/employeeDriverList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Test - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Test2 - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Test2 - lastName"));
    }

    @Test
    public void createDriverTest() throws Exception {
        // Given
        DriverDto driverDto = new DriverDto(null, "Test - firstName", "Test - lastName", false);
        Driver driver = new Driver(null, "Test - firstName", "Test - lastName", false);

        Mockito.when(driverMapper.mapToDriver(driverDto)).thenReturn(driver);
        Mockito.when(driverDbService.saveDriver(driver)).thenReturn(driver);
        Mockito.when(driverMapper.mapToDriverDto(driver)).thenReturn(driverDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(driverDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/car_rental/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Test - lastName"));
    }

    @Test
    public void updateDriverTest() throws Exception {
        // Given
        Long driverId = 1L;
        DriverDto driverDto = new DriverDto(driverId, "Test - updatedFirstName", "Test - updatedLastName", false);
        Driver updatedDriver = new Driver(driverId, "Test - updatedFirstName", "Test - updatedLastName", false);
        DriverDto updatedDriverDto = new DriverDto(driverId, "Test - updatedFirstName", "Test - updatedLastName", false);

        Mockito.when(driverDbService.updateDriver(driverId, driverDto)).thenReturn(updatedDriver);
        Mockito.when(driverMapper.mapToDriverDto(updatedDriver)).thenReturn(updatedDriverDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(driverDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/car_rental/driver/{driverId}", driverId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(driverId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test - updatedFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Test - updatedLastName"));
    }
}
