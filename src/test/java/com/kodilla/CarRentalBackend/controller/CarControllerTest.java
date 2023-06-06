package com.kodilla.CarRentalBackend.controller;

import com.google.gson.Gson;
import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.CarClass;
import com.kodilla.CarRentalBackend.domain.Dto.CarDto;
import com.kodilla.CarRentalBackend.mapper.CarMapper;
import com.kodilla.CarRentalBackend.service.CarDbService;
import org.junit.jupiter.api.Test;
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

import static com.kodilla.CarRentalBackend.domain.CarClass.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarDbService carDbService;

    @MockBean
    private CarMapper carMapper;

    @Test
    public void getCarListTest() throws Exception {
        // Given
        Car car1 = new Car(1L, "Test - brand","Test - model",1.5, ECONOMIC , 5, true, 2020, 10000L, false);
        Car car2 = new Car(2L, "Test2 - brand","Test2 - model",2.0, ECONOMIC , 7, false,  2015, 15000L, false);
        List<Car> cars = Arrays.asList(car1, car2);

        CarDto carDto1 = new CarDto(1L, "Test - brand","Test - model",1.5, ECONOMIC , 5, true, 2020, 10000L, false);
        CarDto carDto2 = new CarDto(2L, "Test2 - brand","Test2 - model",2.0, ECONOMIC , 7, false,  2015, 15000L, false);
        List<CarDto> carDtos = Arrays.asList(carDto1, carDto2);

        Mockito.when(carDbService.getCarList()).thenReturn(cars);
        Mockito.when(carMapper.mapToCarDtoList(cars)).thenReturn(carDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/car")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Test - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manualGearbox").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productionYear").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Test2 - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manualGearbox").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productionYear").value(2015))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mileage").value(15000));
    }

    @Test
    public void getCarTest() throws Exception {
        // Given
        Long carId = 1L;
        Car car = new Car(1L, "Test - brand","Test - model",2.2, ECONOMIC , 7, true,  2020, 10000L, false);;
        CarDto carDto = new CarDto(1L, "Test - brand","Test - model",2.2, ECONOMIC , 7, true,  2020, 10000L, false);;

        Mockito.when(carDbService.getCarById(carId)).thenReturn(car);
        Mockito.when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/car/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Test - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manualGearbox").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productionYear").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mileage").value(10000));
    }

    @Test
    public void getCarsByModelTest() throws Exception {
        // Given
        String carModel = "Test - model";
        Car car1 = new Car(1L, "Test - brand","Test - model",2.0, ECONOMIC , 7, false,  2015, 15000L, false);
        Car car2 = new Car(2L, "Test2 - brand","Test2 - model",2.0, ECONOMIC , 7, false,  2015, 15000L, false);
        List<Car> cars = Arrays.asList(car1, car2);

        CarDto carDto1 = new CarDto(1L, "Test - brand","Test - model",2.0, ECONOMIC , 7, false,  2015, 15000L, false);
        CarDto carDto2 = new CarDto(2L, "Test2 - brand","Test2 - model",2.0, ECONOMIC , 7, false,  2015, 15000L, false);
        List<CarDto> carDtos = Arrays.asList(carDto1, carDto2);

        Mockito.when(carDbService.getCarByModel(carModel)).thenReturn(cars);
        Mockito.when(carDbService.getCommonCars(cars)).thenReturn(cars);
        Mockito.when(carMapper.mapToCarDtoList(cars)).thenReturn(carDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/car/model")
                        .param("carModel", carModel)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Test - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manualGearbox").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productionYear").value(2015))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage").value(15000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Test2 - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manualGearbox").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productionYear").value(2015))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mileage").value(15000));
    }

    @Test
    public void getCarsByClassTest() throws Exception {
        // Given
        CarClass carClass = ECONOMIC;
        Car car1 = new Car(1L, "Test - brand","Test - model",2.2, ECONOMIC , 7, false,  2019, 15000L, false);
        Car car2 = new Car(2L, "Test2 - brand","Test2 - model",2.2, ECONOMIC , 7, false,  2019, 15000L, false);;
        List<Car> cars = Arrays.asList(car1, car2);

        CarDto carDto1 = new CarDto(1L, "Test - brand","Test - model",2.2, ECONOMIC , 7, false,  2019, 15000L, false);
        CarDto carDto2 = new CarDto(2L, "Test2 - brand","Test2 - model",2.2, ECONOMIC , 7, false,  2019, 15000L, false);;
        List<CarDto> carDtos = Arrays.asList(carDto1, carDto2);

        Mockito.when(carDbService.getCarsByCarClass(carClass)).thenReturn(cars);
        Mockito.when(carDbService.getCommonCars(cars)).thenReturn(cars);
        Mockito.when(carMapper.mapToCarDtoList(cars)).thenReturn(carDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/car/class")
                        .param("carClass", carClass.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Test - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manualGearbox").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productionYear").value(2019))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage").value(15000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Test2 - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manualGearbox").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productionYear").value(2019))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mileage").value(15000));
    }

    @Test
    public void getCarsBySeatsNumberTest() throws Exception {
        // Given
        int seatsNumber = 7;
        Car car1 = new Car(1L, "Test - brand","Test - model1",2.2, LUXURY, 7, true,  2020, 10000L, false);;
        Car car2 = new Car(2L, "Test - brand","Test - model2",2.2, LUXURY, seatsNumber, false,  2021, 20000L, false);;
        List<Car> cars = Arrays.asList(car1, car2);

        CarDto carDto1 = new CarDto(1L, "Test - brand","Test - model1",2.2, LUXURY, 7, true,  2020, 10000L, false);;
        CarDto carDto2 = new CarDto(2L, "Test - brand","Test - model2",2.2, LUXURY, seatsNumber, false,  2021, 20000L, false);;
        List<CarDto> carDtos = Arrays.asList(carDto1, carDto2);

        Mockito.when(carDbService.getCarsBySeatsNumber(seatsNumber)).thenReturn(cars);
        Mockito.when(carDbService.getCommonCars(cars)).thenReturn(cars);
        Mockito.when(carMapper.mapToCarDtoList(cars)).thenReturn(carDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/car/seats")
                        .param("seatsNumber", String.valueOf(seatsNumber))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Test - model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(seatsNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manualGearbox").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productionYear").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Test - model2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsNumber").value(seatsNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manualGearbox").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productionYear").value(2021))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mileage").value(20000));
    }

    @Test
    public void getCarsByGearboxTest() throws Exception {
        // Given
        boolean manualGearbox = true;
        Car car1 = new Car(1L, "Test - brand","Test - model1",1.4, TRANSPORT,  5, manualGearbox,  2020, 10000L, false);;
        Car car2 = new Car(2L, "Test - brand","Test - model2",2.5, TRANSPORT,  7,  manualGearbox,  2021, 20000L, false);;
        List<Car> cars = Arrays.asList(car1, car2);

        CarDto carDto1 = new CarDto(1L, "Test - brand","Test - model1",1.4, TRANSPORT,  5, manualGearbox,  2020, 10000L, false);;
        CarDto carDto2 = new CarDto(2L, "Test - brand","Test - model2",2.5, TRANSPORT,  7,  manualGearbox,  2021, 20000L, false);;
        List<CarDto> carDtos = Arrays.asList(carDto1, carDto2);

        Mockito.when(carDbService.getCarsByManualGearbox(manualGearbox)).thenReturn(cars);
        Mockito.when(carDbService.getCommonCars(cars)).thenReturn(cars);
        Mockito.when(carMapper.mapToCarDtoList(cars)).thenReturn(carDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/car/gearbox")
                        .param("manualGearbox", String.valueOf(manualGearbox))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Test - model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manualGearbox").value(manualGearbox))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productionYear").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Test - model2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manualGearbox").value(manualGearbox))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productionYear").value(2021))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mileage").value(20000));
    }

    @Test
    public void getCarsByProductionYearTest() throws Exception {
        // Given
        int productionYear = 1980;
        Car car1 = new Car(1L, "Test - brand","Test - model1",2.5, VINTAGE,  7,  true,  1980, 10000L, false);;
        Car car2 = new Car(2L, "Test - brand","Test - model2",2.5, VINTAGE,  7,  false,  1980, 20000L, false);;
        List<Car> cars = Arrays.asList(car1, car2);

        CarDto carDto1 = new CarDto(1L, "Test - brand","Test - model1",2.5, VINTAGE,  7,  true,  1980, 10000L, false);;
        CarDto carDto2 = new CarDto(2L, "Test - brand","Test - model2",2.5, VINTAGE,  7,  false,  1980, 20000L, false);;
        List<CarDto> carDtos = Arrays.asList(carDto1, carDto2);

        Mockito.when(carDbService.getCarsByProductionYear(productionYear)).thenReturn(cars);
        Mockito.when(carDbService.getCommonCars(cars)).thenReturn(cars);
        Mockito.when(carMapper.mapToCarDtoList(cars)).thenReturn(carDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/car/year")
                        .param("productionYear", String.valueOf(productionYear))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Test - model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manualGearbox").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productionYear").value(productionYear))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Test - model2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manualGearbox").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productionYear").value(productionYear))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mileage").value(20000));
    }

    @Test
    public void getCarsByMileageTest() throws Exception {
        // Given
        Long mileage = 10000L;
        Car car1 = new Car(1L, "Test - brand","Test - model1",2.5, VINTAGE,  5,  true,  2020, mileage, false);;
        Car car2 = new Car(2L, "Test - brand","Test - model2",2.5, VINTAGE,  7,  false,  2021, mileage, false);;
        List<Car> cars = Arrays.asList(car1, car2);

        CarDto carDto1 = new CarDto(1L, "Test - brand","Test - model1",2.5, VINTAGE,  5,  true,  2020, mileage, false);;
        CarDto carDto2 = new CarDto(2L, "Test - brand","Test - model2",2.5, VINTAGE,  7,  false,  2021, mileage, false);;
        List<CarDto> carDtos = Arrays.asList(carDto1, carDto2);

        Mockito.when(carDbService.getCarsByMileage(mileage)).thenReturn(cars);
        Mockito.when(carDbService.getCommonCars(cars)).thenReturn(cars);
        Mockito.when(carMapper.mapToCarDtoList(cars)).thenReturn(carDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/car/mileage")
                        .param("mileage", String.valueOf(mileage))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Test - model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manualGearbox").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productionYear").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage").value(mileage))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Test - model2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsNumber").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manualGearbox").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productionYear").value(2021))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mileage").value(mileage));
    }

    @Test
    public void saveCarTest() throws Exception {
        // Given
        CarDto carDto = new CarDto(1L, "Test - brand","Test - model",1.5, ECONOMIC , 5, true, 2020, 10000L, false);
        Car car = new Car(1L, "Test - brand","Test - model",1.5, ECONOMIC , 5, true, 2020, 10000L, false);

        Mockito.when(carMapper.mapToCar(carDto)).thenReturn(car);
        Mockito.when(carDbService.saveCar(car)).thenReturn(car);
        Mockito.when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        String jsonContent = new Gson().toJson(carDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/car_rental/car")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Test - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manualGearbox").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productionYear").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mileage").value(10000));
    }

    @Test
    public void updateCarTest() throws Exception {
        // Given
        Long carId = 1L;
        CarDto carDto = new CarDto(1L, "Test - brand","Test - model",1.5, ECONOMIC , 5, true, 2020, 10000L, false);
        Car updatedCar = new Car(1L, "Test - brand","Test - model",1.5, ECONOMIC , 5, true, 2020, 10000L, false);
        CarDto updatedCarDto = new CarDto(1L, "Test - brand","Test - model",1.5, ECONOMIC , 5, true, 2020, 10000L, false);

        Mockito.when(carDbService.updateCar(carId, carDto)).thenReturn(updatedCar);
        Mockito.when(carMapper.mapToCarDto(updatedCar)).thenReturn(updatedCarDto);

        String jsonContent = new Gson().toJson(carDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/car_rental/car/{carId}", carId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Test - model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manualGearbox").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productionYear").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mileage").value(10000));
    }

    @Test
    public void deleteCarTest() throws Exception {
        // Given
        Long carId = 1L;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/car_rental/car/{carId}", carId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
