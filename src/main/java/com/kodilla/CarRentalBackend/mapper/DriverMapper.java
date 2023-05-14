package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.domain.Driver;
import com.kodilla.CarRentalBackend.domain.Dto.DriverDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverMapper {

    public DriverDto mapToDriverDto(final Driver driver) {
        return new DriverDto(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.isCompanyEmployee()
        );
    }

    public Driver mapToDriver(final DriverDto driverDto) {
        return new Driver(
                driverDto.getId(),
                driverDto.getFirstName(),
                driverDto.getLastName(),
                driverDto.isCompanyEmployee()
        );
    }

    public List<DriverDto> mapToDriverDtoList(final List<Driver> driverList) {
        return driverList.stream()
                .map(this::mapToDriverDto)
                .collect(Collectors.toList());
    }
}
