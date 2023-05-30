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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationDbService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final ClientRepository clientRepository;
    private final BingMapsClient bingMapsClient;

    public List<Reservation> getReservationList() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(final Long reservationId) throws ReservationNotFoundException {
        return reservationRepository.findById(reservationId).orElseThrow(ReservationNotFoundException::new);
    }

    public List<Reservation> getReservationsByClientId(final Long clientId) {
        return reservationRepository.findAllByClientId(clientId);
    }

    public List<Reservation> getReservationsByCarId(final Long carId) {
        return reservationRepository.findAllByCarId(carId);
    }

    public Reservation saveReservation(final Reservation reservation) throws CarAlreadyReservedException, DriverAlreadyReservedException, DamagedCarException {
        boolean carAvailable = reservationRepository.existsByCarIdAndRentalEndGreaterThanEqualAndRentalStartLessThanEqual(
                reservation.getCar().getId(), reservation.getRentalStart(), reservation.getRentalEnd());
        if (carAvailable) {
            throw new CarAlreadyReservedException();
        }

        boolean driverAvailable = reservationRepository.existsByDriverIdAndRentalEndGreaterThanEqualAndRentalStartLessThanEqual(
                reservation.getDriver().getId(), reservation.getRentalStart(), reservation.getRentalEnd());
        if (driverAvailable) {
            throw new DriverAlreadyReservedException();
        }
        if (reservation.getCar().isDamaged()) {
            throw new DamagedCarException();
        }
        reservation.setPrice(calculatePrice(reservation));
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(final Long reservationId, final ReservationDto reservationDto) throws ReservationNotFoundException, CarNotFoundException, DriverNotFoundException, ClientNotFoundException, CarAlreadyReservedException, DriverAlreadyReservedException, DamagedCarException {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(ReservationNotFoundException::new);
        reservation.setRentalStart(reservationDto.getRentalStart());
        reservation.setRentalEnd(reservationDto.getRentalEnd());
        reservation.setRentalPlace(reservationDto.getRentalPlace());
        reservation.setReturnPlace(reservationDto.getRentalPlace());
        reservation.setCar(carRepository.findById(reservationDto.getCarId()).orElseThrow(CarNotFoundException::new));
        reservation.setDriver(driverRepository.findById(reservationDto.getDriverId()).orElseThrow(DriverNotFoundException::new));
        reservation.setClient(clientRepository.findById(reservationDto.getClientId()).orElseThrow(ClientNotFoundException::new));
        return saveReservation(reservation);
    }

    public void deleteReservation(final Long reservationId) throws ReservationNotFoundException {
        if(!reservationRepository.existsById(reservationId)) {
            throw new ReservationNotFoundException();
        }
        reservationRepository.deleteById(reservationId);
    }

    public double calculatePrice(final Reservation reservation) {
        int rentedDays = calculateRentedDays(reservation.getRentalStart(),reservation.getRentalEnd());
        CarClass carClass = reservation.getCar().getCarClass();
        boolean vipStatus = reservation.getClient().isVipStatus();
        double distance = bingMapsClient.getDistance(reservation.getRentalPlace(),reservation.getReturnPlace());
        boolean driverIsEmployee = reservation.getDriver().isCompanyEmployee();

        double carClassMultiplier = getCarClassMultiplier(carClass);
        double price = 120 * rentedDays * carClassMultiplier;

        if (vipStatus) {
            price *= 0.8;
        }

        double engineCapacity = reservation.getCar().getEngineCapacity();
        double fuelConsumption = engineCapacity * 4;
        price += (distance * 0.01) * fuelConsumption * 8;

        if (driverIsEmployee) {
            price += rentedDays * 60;
        }
        price = Math.round(price * 100.0) / 100.0;
        return price;
    }

    private double getCarClassMultiplier(final CarClass carClass) {
        return switch (carClass) {
            case ECONOMIC -> 1;
            case LUXURY -> 2;
            case SPORTS -> 1.8;
            case VINTAGE -> 1.6;
            case TRANSPORT -> 1.4;
        };
    }

    public int calculateRentedDays(final LocalDate startDate, final LocalDate endDate) {
        long rentedDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        return Math.toIntExact(rentedDays);
    }

}
