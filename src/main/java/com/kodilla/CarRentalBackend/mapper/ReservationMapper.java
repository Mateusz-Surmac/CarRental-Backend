package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.exceptions.CarNotFoundException;
import com.kodilla.CarRentalBackend.exceptions.ClientNotFoundException;
import com.kodilla.CarRentalBackend.exceptions.DriverNotFoundException;
import com.kodilla.CarRentalBackend.domain.Dto.ReservationDto;
import com.kodilla.CarRentalBackend.domain.Reservation;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import com.kodilla.CarRentalBackend.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationMapper {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final ClientRepository clientRepository;

    public ReservationDto mapToReservationDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getRentalStart(),
                reservation.getRentalEnd(),
                reservation.getRentalPlace(),
                reservation.getReturnPlace(),
                reservation.getPrice(),
                reservation.getCar().getId(),
                reservation.getDriver().getId(),
                reservation.getClient().getId()
        );
    }

    public Reservation mapToReservation(ReservationDto reservationDto) throws CarNotFoundException, DriverNotFoundException, ClientNotFoundException {
        return new Reservation(
                reservationDto.getId(),
                reservationDto.getRentalStart(),
                reservationDto.getRentalEnd(),
                reservationDto.getRentalPlace(),
                reservationDto.getReturnPlace(),
                reservationDto.getPrice(),
                carRepository.findById(reservationDto.getCarId()).orElseThrow(CarNotFoundException::new),
                driverRepository.findById(reservationDto.getDriverId()).orElseThrow(DriverNotFoundException::new),
                clientRepository.findById(reservationDto.getClientId()).orElseThrow(ClientNotFoundException::new)
        );
    }

    public List<ReservationDto> mapToReservationDtoList(final List<Reservation> reservationList) {
        return reservationList.stream()
                .map(this::mapToReservationDto)
                .collect(Collectors.toList());
    }
}
