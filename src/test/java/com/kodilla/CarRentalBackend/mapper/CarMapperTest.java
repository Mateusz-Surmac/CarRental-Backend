package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.CarClass;
import com.kodilla.CarRentalBackend.domain.Dto.CarDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class CarMapperTest {

    private final CarMapper carMapper = new CarMapper();

    @Test
    public void shouldMapCarToCarDto() {
        // Given
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setEngineCapacity(1.8);
        car.setCarClass(CarClass.ECONOMIC);
        car.setSeatsNumber(5);
        car.setManualGearbox(true);
        car.setProductionYear(2020);
        car.setMileage(5000L);
        car.setDamaged(false);

        // When
        CarDto carDto = carMapper.mapToCarDto(car);

        // Then
        assertEquals(car.getId(), carDto.getId());
        assertEquals(car.getBrand(), carDto.getBrand());
        assertEquals(car.getModel(), carDto.getModel());
        assertEquals(car.getEngineCapacity(), carDto.getEngineCapacity());
        assertEquals(car.getCarClass(), carDto.getCarClass());
        assertEquals(car.getSeatsNumber(), carDto.getSeatsNumber());
        assertEquals(car.isManualGearbox(), carDto.isManualGearbox());
        assertEquals(car.getProductionYear(), carDto.getProductionYear());
        assertEquals(car.getMileage(), carDto.getMileage());
        assertEquals(car.isDamaged(), carDto.isDamaged());
    }

    @Test
    public void shouldMapCarDtoToCar() {
        // Given
        CarDto carDto = new CarDto();
        carDto.setId(1L);
        carDto.setBrand("Toyota");
        carDto.setModel("Corolla");
        carDto.setEngineCapacity(1.8);
        carDto.setCarClass(CarClass.ECONOMIC);
        carDto.setSeatsNumber(5);
        carDto.setManualGearbox(true);
        carDto.setProductionYear(2020);
        carDto.setMileage(50000L);
        carDto.setDamaged(false);

        // When
        Car car = carMapper.mapToCar(carDto);

        // Then
        assertEquals(carDto.getId(), car.getId());
        assertEquals(carDto.getBrand(), car.getBrand());
        assertEquals(carDto.getModel(), car.getModel());
        assertEquals(carDto.getEngineCapacity(), car.getEngineCapacity());
        assertEquals(carDto.getCarClass(), car.getCarClass());
        assertEquals(carDto.getSeatsNumber(), car.getSeatsNumber());
        assertEquals(carDto.isManualGearbox(), car.isManualGearbox());
        assertEquals(carDto.getProductionYear(), car.getProductionYear());
        assertEquals(carDto.getMileage(), car.getMileage());
        assertEquals(carDto.isDamaged(), car.isDamaged());
    }

    @Test
    public void shouldMapCarListToCarDtoList() {
        // Given
        List<Car> carList = new ArrayList<>();
        Car car1 = new Car();
        car1.setId(1L);
        car1.setBrand("Toyota");
        car1.setModel("Corolla");
        car1.setEngineCapacity(1.8);
        car1.setCarClass(CarClass.ECONOMIC);
        car1.setSeatsNumber(5);
        car1.setManualGearbox(true);
        car1.setProductionYear(2020);
        car1.setMileage(50000L);
        car1.setDamaged(false);
        carList.add(car1);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setBrand("Honda");
        car2.setModel("Civic");
        car2.setEngineCapacity(1.6);
        car2.setCarClass(CarClass.ECONOMIC);
        car2.setSeatsNumber(5);
        car2.setManualGearbox(true);
        car2.setProductionYear(2021);
        car2.setMileage(40000L);
        car2.setDamaged(true);
        carList.add(car2);

        // When
        List<CarDto> carDtoList = carMapper.mapToCarDtoList(carList);

        // Then
        assertEquals(carList.size(), carDtoList.size());

        for (int i = 0; i < carList.size(); i++) {
            Car car = carList.get(i);
            CarDto carDto = carDtoList.get(i);

            assertEquals(car.getId(), carDto.getId());
            assertEquals(car.getBrand(), carDto.getBrand());
            assertEquals(car.getModel(), carDto.getModel());
            assertEquals(car.getEngineCapacity(), carDto.getEngineCapacity());
            assertEquals(car.getCarClass(), carDto.getCarClass());
            assertEquals(car.getSeatsNumber(), carDto.getSeatsNumber());
            assertEquals(car.isManualGearbox(), carDto.isManualGearbox());
            assertEquals(car.getProductionYear(), carDto.getProductionYear());
            assertEquals(car.getMileage(), carDto.getMileage());
            assertEquals(car.isDamaged(), carDto.isDamaged());
        }
    }
}
