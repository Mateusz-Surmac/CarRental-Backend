package com.kodilla.CarRentalBackend.controller;

import com.kodilla.CarRentalBackend.controller.exceptions.DriverNotFoundException;
import com.kodilla.CarRentalBackend.domain.Driver;
import com.kodilla.CarRentalBackend.domain.Dto.DriverDto;
import com.kodilla.CarRentalBackend.domain.Reservation;
import com.kodilla.CarRentalBackend.mapper.DriverMapper;
import com.kodilla.CarRentalBackend.repository.DriverRepository;
import com.kodilla.CarRentalBackend.service.DriverDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/car_rental/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverDbService driverDbService;
    private final DriverMapper driverMapper;

    @GetMapping
    private ResponseEntity<List<DriverDto>> getDriverList() {
        return ResponseEntity.ok(driverMapper.mapToDriverDtoList(driverDbService.getDriverList()));
    }

    @GetMapping(value = "{driverId}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable Long driverId) throws DriverNotFoundException {
        return ResponseEntity.ok(driverMapper.mapToDriverDto(driverDbService.getDriverById(driverId)));
    }

    @GetMapping(value = "employeeDriverList")
    public ResponseEntity<List<DriverDto>> getEmployeeDriverList() {
        return ResponseEntity.ok(driverMapper.mapToDriverDtoList(driverDbService.getEmployeeDriversList()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDto> createDriver(@RequestBody DriverDto driverDto) {
        Driver driver = driverMapper.mapToDriver(driverDto);
        driverDbService.saveDriver(driver);
        return ResponseEntity.ok(driverMapper.mapToDriverDto(driver));
    }

    @PutMapping(value = "{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDto> updateDriver(@PathVariable Long driverId, @RequestBody DriverDto driverDto) throws DriverNotFoundException {
        Driver updatedDriver = driverDbService.updateDriver(driverId,driverDto);
        DriverDto updatedDriverDto = driverMapper.mapToDriverDto(updatedDriver);
        return ResponseEntity.ok(updatedDriverDto);
    }
}
