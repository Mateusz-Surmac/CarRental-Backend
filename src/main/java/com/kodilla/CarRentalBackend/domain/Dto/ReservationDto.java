package com.kodilla.CarRentalBackend.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    private LocalDate rentalStart;
    private LocalDate rentalEnd;
    private String rentalPlace;
    private String returnPlace;
    private double price;
    private Long carId;
    private Long driverId;
    private Long clientId;
}
