package com.kodilla.CarRentalBackend.controller;

import com.kodilla.CarRentalBackend.domain.Dto.ReservationDto;
import com.kodilla.CarRentalBackend.domain.Reservation;
import com.kodilla.CarRentalBackend.exceptions.*;
import com.kodilla.CarRentalBackend.mapper.ReservationMapper;
import com.kodilla.CarRentalBackend.service.ReservationDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/car_rental/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationDbService reservationDbService;
    private final ReservationMapper reservationMapper;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getReservationList() {
        return ResponseEntity.ok(reservationMapper.mapToReservationDtoList(reservationDbService.getReservationList()));
    }

    @GetMapping(value = "{reservationId}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long reservationId) throws ReservationNotFoundException {
        return ResponseEntity.ok(reservationMapper.mapToReservationDto(reservationDbService.getReservationById(reservationId)));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByClientId(@PathVariable Long clientId) {
        List<Reservation> reservations = reservationDbService.getReservationsByClientId(clientId);
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByCarId(@PathVariable Long carId) {
        List<Reservation> reservations = reservationDbService.getReservationsByCarId(carId);
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) throws CarNotFoundException, DriverNotFoundException, ClientNotFoundException, CarAlreadyReservedException, DriverAlreadyReservedException, DamagedCarException {
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservationDbService.saveReservation(reservation);
        return ResponseEntity.ok(reservationMapper.mapToReservationDto(reservation));
    }

    @PutMapping(value = "{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long reservationId, @RequestBody ReservationDto reservationDto) throws ReservationNotFoundException, CarNotFoundException, DriverNotFoundException, ClientNotFoundException, CarAlreadyReservedException, DriverAlreadyReservedException, DamagedCarException {
        Reservation updatedReservation = reservationDbService.updateReservation(reservationId, reservationDto);
        ReservationDto updatedReservationDto = reservationMapper.mapToReservationDto(updatedReservation);
        return ResponseEntity.ok(updatedReservationDto);
    }

    @DeleteMapping(value = "{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) throws ReservationNotFoundException {
        reservationDbService.deleteReservation(reservationId);
        return ResponseEntity.ok().build();
    }
}
