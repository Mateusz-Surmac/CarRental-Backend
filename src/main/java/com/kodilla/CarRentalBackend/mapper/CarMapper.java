package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.Dto.CarDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMapper {

    public CarDto mapToCarDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getEngineCapacity(),
                car.getCarClass(),
                car.getSeatsNumber(),
                car.isManualGearbox(),
                car.getProductionYear(),
                car.getMileage(),
                car.isDamaged()
        );
    }

    public Car mapToCar(final CarDto carDto) {
        return new Car(
                carDto.getId(),
                carDto.getBrand(),
                carDto.getModel(),
                carDto.getEngineCapacity(),
                carDto.getCarClass(),
                carDto.getSeatsNumber(),
                carDto.isManualGearbox(),
                carDto.getProductionYear(),
                carDto.getMileage(),
                carDto.isDamaged()
        );
    }

    public List<CarDto> mapToCarDtoList(final List<Car> carList) {
        return carList.stream()
                .map(this::mapToCarDto)
                .collect(Collectors.toList());
    }
}
