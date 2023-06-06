package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Driver;
import com.kodilla.CarRentalBackend.domain.Dto.ReservationDto;
import com.kodilla.CarRentalBackend.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationMapperTest {


    @InjectMocks
    private ReservationMapper reservationMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldMapReservationToReservationDto() {
        // Given
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setRentalStart(LocalDate.of(2023, 1, 1));
        reservation.setRentalEnd(LocalDate.of(2023, 1, 3));
        reservation.setRentalPlace("Location A");
        reservation.setReturnPlace("Location B");
        reservation.setPrice(200.0);
        reservation.setCar(new Car());
        reservation.getCar().setId(2L);
        reservation.setDriver(new Driver());
        reservation.getDriver().setId(3L);
        reservation.setClient(new Client());
        reservation.getClient().setId(4L);

        // When
        ReservationDto reservationDto = reservationMapper.mapToReservationDto(reservation);

        // Then
        assertEquals(reservation.getId(), reservationDto.getId());
        assertEquals(reservation.getRentalStart(), reservationDto.getRentalStart());
        assertEquals(reservation.getRentalEnd(), reservationDto.getRentalEnd());
        assertEquals(reservation.getRentalPlace(), reservationDto.getRentalPlace());
        assertEquals(reservation.getReturnPlace(), reservationDto.getReturnPlace());
        assertEquals(reservation.getPrice(), reservationDto.getPrice());
        assertEquals(reservation.getCar().getId(), reservationDto.getCarId());
        assertEquals(reservation.getDriver().getId(), reservationDto.getDriverId());
        assertEquals(reservation.getClient().getId(), reservationDto.getClientId());
    }

    @Test
    public void shouldMapReservationListToReservationDtoList() {
        // Given
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setRentalStart(LocalDate.of(2023, 1, 1));
        reservation1.setRentalEnd(LocalDate.of(2023, 1, 3));
        reservation1.setRentalPlace("Location A");
        reservation1.setReturnPlace("Location B");
        reservation1.setPrice(200.0);
        reservation1.setCar(new Car());
        reservation1.getCar().setId(2L);
        reservation1.setDriver(new Driver());
        reservation1.getDriver().setId(3L);
        reservation1.setClient(new Client());
        reservation1.getClient().setId(4L);
        reservationList.add(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setId(5L);
        reservation2.setRentalStart(LocalDate.of(2023, 2, 1));
        reservation2.setRentalEnd(LocalDate.of(2023, 2, 3));
        reservation2.setRentalPlace("Location C");
        reservation2.setReturnPlace("Location D");
        reservation2.setPrice(300.0);
        reservation2.setCar(new Car());
        reservation2.getCar().setId(6L);
        reservation2.setDriver(new Driver());
        reservation2.getDriver().setId(7L);
        reservation2.setClient(new Client());
        reservation2.getClient().setId(8L);
        reservationList.add(reservation2);

        // When
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservationList);

        // Then
        assertEquals(reservationList.size(), reservationDtoList.size());

        for (int i = 0; i < reservationList.size(); i++) {
            Reservation reservation = reservationList.get(i);
            ReservationDto reservationDto = reservationDtoList.get(i);

            assertEquals(reservation.getId(), reservationDto.getId());
            assertEquals(reservation.getRentalStart(), reservationDto.getRentalStart());
            assertEquals(reservation.getRentalEnd(), reservationDto.getRentalEnd());
            assertEquals(reservation.getRentalPlace(), reservationDto.getRentalPlace());
            assertEquals(reservation.getReturnPlace(), reservationDto.getReturnPlace());
            assertEquals(reservation.getPrice(), reservationDto.getPrice());
            assertEquals(reservation.getCar().getId(), reservationDto.getCarId());
            assertEquals(reservation.getDriver().getId(), reservationDto.getDriverId());
            assertEquals(reservation.getClient().getId(), reservationDto.getClientId());
        }
    }
}

