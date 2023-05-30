package com.kodilla.CarRentalBackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RENTAL_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "RENTAL_ORDER_ID", unique = true)
    private Long id;

    @Column(name = "COST")
    private double cost;

    @Column(name = "COST_PAID")
    private double costPaid;

    @Column(name = "ORDER_STATUS")
    private OrderStatus orderStatus;

    @DecimalMin(value = "0.1")
    @DecimalMax(value = "1.0")
    @Column(name = "FUEL_LEVEL")
    private double fuelLevel;

    @Min(value = 0)
    @Column(name = "DRIVEN_KILOMETERS")
    private Long drivenKilometers;

    @OneToOne
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DAMAGE_ID")
    private Damage damage;

    public RentalOrder(double fuelLevel, Long drivenKilometers, Reservation reservation) {
        this.fuelLevel = fuelLevel;
        this.drivenKilometers = drivenKilometers;
        this.reservation = reservation;
    }
}
