package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.exceptions.DriverNotFoundException;
import com.kodilla.CarRentalBackend.domain.Driver;
import com.kodilla.CarRentalBackend.domain.Dto.DriverDto;
import com.kodilla.CarRentalBackend.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverDbService {

    private final DriverRepository driverRepository;

    public List<Driver> getDriverList() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(final Long driverId) throws DriverNotFoundException {
        return driverRepository.findById(driverId).orElseThrow(DriverNotFoundException::new);
    }

    public List<Driver> getEmployeeDriversList() {
        return driverRepository.findAll().stream()
                .filter(Driver::isCompanyEmployee)
                .collect(Collectors.toList());
    }

    public Driver saveDriver(final Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(final Long driverId, final DriverDto driverDto) throws DriverNotFoundException {
        Driver driver = driverRepository.findById(driverId).orElseThrow(DriverNotFoundException::new);
        driver.setFirstName(driverDto.getFirstName());
        driver.setLastName(driverDto.getLastName());
        driver.setCompanyEmployee(driverDto.isCompanyEmployee());
        return driverRepository.save(driver);
    }
}
