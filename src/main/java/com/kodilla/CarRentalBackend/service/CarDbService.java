package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.exceptions.CarNotFoundException;
import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.CarClass;
import com.kodilla.CarRentalBackend.domain.Dto.CarDto;
import com.kodilla.CarRentalBackend.repository.CarRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarDbService {

    private final CarRepository carRepository;
    private List<Car> filteredList;

    @PostConstruct
    private void init() {
        filteredList = getCarList();
    }

    public List<Car> getCarList() {
        return carRepository.findAll();
    }

    public Car getCarById(final Long carId) throws CarNotFoundException {
        return carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
    }

    public List<Car> getCarByModel(final String carModel) {
        return carRepository.findAllByModel(carModel);
    }

    public List<Car> getCarsByCarClass(CarClass carClass) {
        return carRepository.findAllByCarClass(carClass);
    }

    public List<Car> getCarsBySeatsNumber(int seatsNumber) {
        return carRepository.findAllBySeatsNumberGreaterThanEqual(seatsNumber);
    }

    public List<Car> getCarsByManualGearbox(boolean manualGearbox) {
        return carRepository.findAllByManualGearbox(manualGearbox);
    }

    public List<Car> getCarsByProductionYear(int productionYear) {
        return carRepository.findAllByProductionYearGreaterThanEqual(productionYear);
    }

    public List<Car> getCarsByMileage(final Long mileage) {
        return carRepository.findAllByMileageLessThanEqual(mileage);
    }

    public Car saveCar(final Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(final Long carId, final CarDto carDto) throws CarNotFoundException {
        Car car = carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setEngineCapacity(carDto.getEngineCapacity());
        car.setCarClass(carDto.getCarClass());
        car.setSeatsNumber(carDto.getSeatsNumber());
        car.setManualGearbox(carDto.isManualGearbox());
        car.setProductionYear(carDto.getProductionYear());
        car.setMileage(carDto.getMileage());
        car.setDamaged(carDto.isDamaged());
        return carRepository.save(car);
    }

    public void deleteCar(final Long carId) throws CarNotFoundException {
        if (!carRepository.existsById(carId)) {
            throw new CarNotFoundException();
        }
        carRepository.deleteById(carId);
    }
    public List<Car> getCommonCars(List<Car> newCars) {
        filteredList.retainAll(newCars);
        return filteredList;
    }
}
