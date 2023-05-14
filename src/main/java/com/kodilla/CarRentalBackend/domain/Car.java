package com.kodilla.CarRentalBackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "CAR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "CAR_ID", unique = true)
    private Long id;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "ENGINE_CAPACITY")
    private double engineCapacity;

    @Column(name = "CAR_CLASS")
    private CarClass carClass;

    @Column(name = "SEATS_NUMBER")
    private int seatsNumber;

    @Column(name = "MANUAL_GEARBOX")
    private boolean manualGearbox;

    @Column(name = "PRODUCTION_YEAR")
    private int productionYear;

    @Column(name ="MILAGE")
    private Long mileage;

    @Column(name = "DAMAGED")
    private boolean damaged;

    @OneToMany(
            mappedBy = "car",
            targetEntity = Reservation.class,
            cascade = CascadeType.MERGE
    )
    private List<Reservation> reservations;

    public Car(String brand, String model, double engineCapacity, CarClass carClass, int seatsNumber, boolean manualGearbox, int productionYear, Long mileage) {
        this.brand = brand;
        this.model = model;
        this.engineCapacity = engineCapacity;
        this.carClass = carClass;
        this.seatsNumber = seatsNumber;
        this.manualGearbox = manualGearbox;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.damaged = false;
    }

    public Car(Long id, String brand, String model, double engineCapacity, CarClass carClass, int seatsNumber, boolean manualGearbox, int productionYear, Long mileage, boolean damaged) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.engineCapacity = engineCapacity;
        this.carClass = carClass;
        this.seatsNumber = seatsNumber;
        this.manualGearbox = manualGearbox;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.damaged = damaged;
    }
}
