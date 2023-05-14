package com.kodilla.CarRentalBackend.domain.Dto;

import com.kodilla.CarRentalBackend.domain.CarClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Long id;
    private String brand;
    private String model;
    private double engineCapacity;
    private CarClass carClass;
    private int seatsNumber;
    private boolean manualGearbox;
    private int productionYear;
    private Long mileage;
    private boolean damaged;
}
