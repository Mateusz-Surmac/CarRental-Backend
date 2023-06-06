package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.api.bingMaps.client.BingMapsClient;
import com.kodilla.CarRentalBackend.domain.CarClass;
import com.kodilla.CarRentalBackend.domain.Dto.ReservationDto;
import com.kodilla.CarRentalBackend.domain.Reservation;
import com.kodilla.CarRentalBackend.exceptions.*;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import com.kodilla.CarRentalBackend.repository.DriverRepository;
import com.kodilla.CarRentalBackend.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReservationDbServiceTest {

    private ReservationDbService reservationDbService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BingMapsClient bingMapsClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationDbService = new ReservationDbService(reservationRepository, carRepository, driverRepository, clientRepository, bingMapsClient);
    }

    @Test
    void shouldReturnReservationList() {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());
        when(reservationRepository.findAll()).thenReturn(reservations);

        // When
        List<Reservation> result = reservationDbService.getReservationList();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnReservationById() throws ReservationNotFoundException {
        // Given
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        // When
        Reservation result = reservationDbService.getReservationById(reservationId);

        // Then
        assertEquals(reservationId, result.getId());
    }

    @Test
    void shouldReturnReservationsByClientId() {
        // Given
        Long clientId = 1L;
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());
        when(reservationRepository.findAllByClientId(clientId)).thenReturn(reservations);

        // When
        List<Reservation> result = reservationDbService.getReservationsByClientId(clientId);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnReservationsByCarId() {
        // Given
        Long carId = 1L;
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());
        when(reservationRepository.findAllByCarId(carId)).thenReturn(reservations);

        // When
        List<Reservation> result = reservationDbService.getReservationsByCarId(carId);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldCalculateRentedDays() {
        // Given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);

        // When
        int result = reservationDbService.calculateRentedDays(startDate, endDate);

        // Then
        assertEquals(6, result);
    }
}