package com.kodilla.CarRentalBackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "DRIVER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "DRIVER_ID", unique = true)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "COMPANY_EMPLOYEE")
    private boolean companyEmployee;

    @OneToMany(
            mappedBy = "driver",
            targetEntity = Reservation.class,
            cascade = CascadeType.MERGE
    )
    private List<Reservation> reservations;

    public Driver(String firstName, String lastName, boolean companyEmployee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyEmployee = companyEmployee;
    }

    public Driver(Long id, String firstName, String lastName, boolean companyEmployee) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyEmployee = companyEmployee;
    }
}
