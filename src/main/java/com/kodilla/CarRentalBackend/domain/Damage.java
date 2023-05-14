package com.kodilla.CarRentalBackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "DAMAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Damage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "DAMAGE_ID", unique = true)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "COST")
    private double cost;

    @OneToOne
    @JoinColumn(name = "CAR_ID")
    private Car car;

    public Damage(String description, LocalDate date, double cost, Car car) {
        this.description = description;
        this.date = date;
        this.cost = cost;
        this.car = car;
    }
}
