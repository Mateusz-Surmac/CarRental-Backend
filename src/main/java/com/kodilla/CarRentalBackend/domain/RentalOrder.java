package com.kodilla.CarRentalBackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private BigDecimal cost;

    @Column(name = "COST_PAID")
    private BigDecimal costPaid;

    @Column(name = "ORDER_STATUS")
    private OrderStatus orderStatus;

    @Column(name = "FUEL_LEVEL")
    private double fuelLevel;

    @Column(name = "DRIVEN_KILOMETERS")
    private Long drivenKilometers;

    @OneToOne
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "DAMAGE_ID")
    private Damage damage;

    public RentalOrder(BigDecimal cost, BigDecimal costPaid, OrderStatus orderStatus, double fuelLevel, Long drivenKilometers, Reservation reservation, Damage damage) {
        this.cost = cost;
        this.costPaid = costPaid;
        this.orderStatus = orderStatus;
        this.fuelLevel = fuelLevel;
        this.drivenKilometers = drivenKilometers;
        this.reservation = reservation;
        this.damage = damage;
    }
}
