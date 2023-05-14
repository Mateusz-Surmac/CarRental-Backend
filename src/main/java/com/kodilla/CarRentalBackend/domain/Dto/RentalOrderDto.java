package com.kodilla.CarRentalBackend.domain.Dto;

import com.kodilla.CarRentalBackend.domain.OrderStatus;
import com.kodilla.CarRentalBackend.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalOrderDto {
    private Long id;
    private BigDecimal cost;
    private BigDecimal costPaid;
    private OrderStatus orderStatus;
    private double fuelLevel;
    private Long drivenKilometers;
    private Long reservationId;
    private Long damageId;
}
