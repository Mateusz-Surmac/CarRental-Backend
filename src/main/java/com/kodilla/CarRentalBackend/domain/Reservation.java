package com.kodilla.CarRentalBackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "RESERVATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "RESERVATION_ID", unique = true)
    private Long id;

    @Column(name = "RENTAL_START")
    private LocalDate rentalStart;

    @Column(name = "RENTAL_END")
    private LocalDate rentalEnd;

    @Column(name = "RENTAL_PLACE")
    private String rentalPlace;

    @Column(name = "RETURN_PLACE")
    private String returnPlace;

    @Column(name = "PRICE")
    private double price;

    @ManyToOne
    @JoinColumn(name = "CAR_ID")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    public Reservation(LocalDate rentalStart, LocalDate rentalEnd, String rentalPlace, String returnPlace, Car car, Driver driver, Client client) {
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.rentalPlace = rentalPlace;
        this.returnPlace = returnPlace;
        this.car = car;
        this.driver = driver;
        this.client = client;
    }
}
