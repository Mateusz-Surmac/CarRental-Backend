package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.CarClass;
import com.kodilla.CarRentalBackend.domain.Dto.CarDto;
import com.kodilla.CarRentalBackend.exceptions.CarNotFoundException;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CarDbServiceTest {

    private CarDbService carDbService;
    private CarRepository carRepository;
    private List<Car> testCars;

    @BeforeEach
    public void setUp() {
        carRepository = mock(CarRepository.class);
        carDbService = new CarDbService(carRepository);

        testCars = new ArrayList<>();
        Car car1 = new Car();
        car1.setId(1L);
        car1.setModel("Model A");
        testCars.add(car1);
        Car car2 = new Car();
        car2.setId(2L);
        car2.setModel("Model B");
        testCars.add(car2);
    }

    @AfterEach
    public void cleanUp() {
        for (Car car : testCars) {
            if (carRepository.existsById(car.getId())) {
                carRepository.deleteById(car.getId());
            }
        }
    }

    @Test
    public void shouldGetCarList() {
        // Given
        when(carRepository.findAll()).thenReturn(testCars);

        // When
        List<Car> result = carDbService.getCarList();

        // Then
        Assertions.assertEquals(testCars, result);
    }

    @Test
    public void shouldGetCarById() throws CarNotFoundException {
        // Given
        Car car = testCars.get(0);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        // When
        Car result = carDbService.getCarById(car.getId());

        // Then
        Assertions.assertEquals(car, result);
    }

    @Test
    public void shouldThrowCarNotFoundExceptionWhenGettingCarById() {
        // Given
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(CarNotFoundException.class, () -> {
            // When
            carDbService.getCarById(1L);
        });
    }

    @Test
    public void shouldGetCarByModel() {
        // Given
        String model = "Model A";
        when(carRepository.findAllByModel(model)).thenReturn(testCars);

        // When
        List<Car> result = carDbService.getCarByModel(model);

        // Then
        Assertions.assertEquals(testCars, result);
    }

    @Test
    public void shouldGetCarsByCarClass() {
        // Given
        CarClass carClass = CarClass.ECONOMIC;
        when(carRepository.findAllByCarClass(carClass)).thenReturn(testCars);

        // When
        List<Car> result = carDbService.getCarsByCarClass(carClass);

        // Then
        Assertions.assertEquals(testCars, result);
    }

    @Test
    public void shouldGetCarsBySeatsNumber() {
        // Given
        int seatsNumber = 5;
        when(carRepository.findAllBySeatsNumberGreaterThanEqual(seatsNumber)).thenReturn(testCars);

        // When
        List<Car> result = carDbService.getCarsBySeatsNumber(seatsNumber);

        // Then
        Assertions.assertEquals(testCars, result);
    }

    @Test
    public void shouldGetCarsByManualGearbox() {
        // Given
        boolean manualGearbox = true;
        when(carRepository.findAllByManualGearbox(manualGearbox)).thenReturn(testCars);

        // When
        List<Car> result = carDbService.getCarsByManualGearbox(manualGearbox);

        // Then
        Assertions.assertEquals(testCars, result);
    }

    @Test
    public void shouldGetCarsByProductionYear() {
        // Given
        int productionYear = 2020;
        when(carRepository.findAllByProductionYearGreaterThanEqual(productionYear)).thenReturn(testCars);

        // When
        List<Car> result = carDbService.getCarsByProductionYear(productionYear);

        // Then
        Assertions.assertEquals(testCars, result);
    }

    @Test
    public void shouldGetCarsByMileage() {
        // Given
        long mileage = 100_000L;
        when(carRepository.findAllByMileageLessThanEqual(mileage)).thenReturn(testCars);

        // When
        List<Car> result = carDbService.getCarsByMileage(mileage);

        // Then
        Assertions.assertEquals(testCars, result);
    }

    @Test
    public void shouldSaveCar() {
        // Given
        Car car = new Car();
        car.setId(123L);
        car.setModel("Model C");
        when(carRepository.save(car)).thenReturn(car);
        when(carRepository.existsById(car.getId())).thenReturn(true); // Konfiguracja mockito

        // When
        Car result = carDbService.saveCar(car);

        // Then
        Assertions.assertEquals(car, result);
        Assertions.assertTrue(carRepository.existsById(car.getId()));
    }


    @Test
    public void shouldUpdateCar() throws CarNotFoundException {
        // Given
        Car car = testCars.get(0);
        CarDto carDto = new CarDto();
        carDto.setBrand("Brand X");
        carDto.setModel("Model X");
        carDto.setEngineCapacity(2.0);
        carDto.setCarClass(CarClass.ECONOMIC);
        carDto.setSeatsNumber(7);
        carDto.setManualGearbox(true);
        carDto.setProductionYear(2022);
        carDto.setMileage(50_000L);
        carDto.setDamaged(true);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);

        // When
        Car result = carDbService.updateCar(car.getId(), carDto);

        // Then
        Assertions.assertEquals(car, result);
        Assertions.assertEquals(carDto.getBrand(), car.getBrand());
        Assertions.assertEquals(carDto.getModel(), car.getModel());
        Assertions.assertEquals(carDto.getEngineCapacity(), car.getEngineCapacity());
        Assertions.assertEquals(carDto.getCarClass(), car.getCarClass());
        Assertions.assertEquals(carDto.getSeatsNumber(), car.getSeatsNumber());
        Assertions.assertEquals(carDto.isManualGearbox(), car.isManualGearbox());
        Assertions.assertEquals(carDto.getProductionYear(), car.getProductionYear());
        Assertions.assertEquals(carDto.getMileage(), car.getMileage());
        Assertions.assertEquals(carDto.isDamaged(), car.isDamaged());
    }

    @Test
    public void shouldThrowCarNotFoundExceptionWhenUpdatingCar() {
        // Given
        CarDto carDto = new CarDto();
        carDto.setModel("Model X");
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(CarNotFoundException.class, () -> {
            // When
            carDbService.updateCar(1L, carDto);
        });
    }

    @Test
    public void shouldDeleteCar() throws CarNotFoundException {
        // Given
        Car car = testCars.get(0);
        when(carRepository.existsById(car.getId())).thenReturn(true);

        // When
        carDbService.deleteCar(car.getId());

        // Then
        verify(carRepository, times(1)).deleteById(car.getId());
    }

    @Test
    public void shouldThrowCarNotFoundExceptionWhenDeletingCar() {
        // Given
        when(carRepository.existsById(1L)).thenReturn(false);

        // Then
        Assertions.assertThrows(CarNotFoundException.class, () -> {
            // When
            carDbService.deleteCar(1L);
        });
    }
}
