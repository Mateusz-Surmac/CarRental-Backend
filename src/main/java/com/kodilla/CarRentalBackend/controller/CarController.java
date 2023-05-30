package com.kodilla.CarRentalBackend.controller;

import com.kodilla.CarRentalBackend.exceptions.CarNotFoundException;
import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.CarClass;
import com.kodilla.CarRentalBackend.domain.Dto.CarDto;
import com.kodilla.CarRentalBackend.mapper.CarMapper;
import com.kodilla.CarRentalBackend.service.CarDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/car_rental/car")
@RequiredArgsConstructor
public class CarController {

    private final CarDbService carDbService;
    private final CarMapper carMapper;

    @GetMapping
    public ResponseEntity<List<CarDto>> getCarList() {
        return ResponseEntity.ok(carMapper.mapToCarDtoList(carDbService.getCarList()));
    }

    @GetMapping("{carId}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long carId) throws CarNotFoundException {
        return ResponseEntity.ok(carMapper.mapToCarDto(carDbService.getCarById(carId)));
    }

    @GetMapping("/model")
    public ResponseEntity<List<CarDto>> getCarsByModel(@RequestParam("carModel") String carModel) {
        List<CarDto> filteredList = carMapper.mapToCarDtoList(carDbService.getCommonCars(carDbService.getCarByModel(carModel)));
        return ResponseEntity.ok(filteredList);
    }

    @GetMapping("/class")
    public ResponseEntity<List<CarDto>> getCarsByClass(@RequestParam("carClass") CarClass carClass) {
        List<CarDto> filteredList = carMapper.mapToCarDtoList(carDbService.getCommonCars(carDbService.getCarsByCarClass(carClass)));
        return ResponseEntity.ok(filteredList);
    }

    @GetMapping("/seats")
    public ResponseEntity<List<CarDto>> getCarsBySeatsNumber(@RequestParam("seatsNumber") int seatsNumber) {
        List<CarDto> filteredList = carMapper.mapToCarDtoList(carDbService.getCommonCars(carDbService.getCarsBySeatsNumber(seatsNumber)));
        return ResponseEntity.ok(filteredList);
    }

    @GetMapping("/gearbox")
    public ResponseEntity<List<CarDto>> getCarsByGearbox(@RequestParam("manualGearbox") boolean manualGearbox) {
        List<CarDto> filteredList = carMapper.mapToCarDtoList(carDbService.getCommonCars(carDbService.getCarsByManualGearbox(manualGearbox)));
        return ResponseEntity.ok(filteredList);
    }

    @GetMapping("/year")
    public ResponseEntity<List<CarDto>> getCarsByProductionYear(@RequestParam("productionYear") int productionYear) {
        List<CarDto> filteredList = carMapper.mapToCarDtoList(carDbService.getCommonCars(carDbService.getCarsByProductionYear(productionYear)));
        return ResponseEntity.ok(filteredList);
    }

    @GetMapping("/mileage")
    public ResponseEntity<List<CarDto>> getCarsByMileage(@RequestParam("mileage") Long mileage) {
        List<CarDto> filteredList = carMapper.mapToCarDtoList(carDbService.getCommonCars(carDbService.getCarsByMileage(mileage)));
        return ResponseEntity.ok(filteredList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> saveCar(@RequestBody CarDto carDto) {
        Car car = carMapper.mapToCar(carDto);
        carDbService.saveCar(car);
        return ResponseEntity.ok(carMapper.mapToCarDto(car));
    }

    @PutMapping(value = "{carId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> updateCar(@PathVariable Long carId, @RequestBody CarDto carDto) throws CarNotFoundException {
        Car updatedCar = carDbService.updateCar(carId, carDto);
        CarDto updatedCarDto = carMapper.mapToCarDto(updatedCar);
        return ResponseEntity.ok(updatedCarDto);
    }

    @DeleteMapping(value = "{carId}")
    private ResponseEntity<Void> deleteCar(@PathVariable Long carId) throws CarNotFoundException {
        carDbService.deleteCar(carId);
        return ResponseEntity.ok().build();
    }
}
