package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.exceptions.DamageNotFoundException;
import com.kodilla.CarRentalBackend.exceptions.ReservationNotFoundException;
import com.kodilla.CarRentalBackend.domain.Damage;
import com.kodilla.CarRentalBackend.domain.Dto.RentalOrderDto;
import com.kodilla.CarRentalBackend.domain.RentalOrder;
import com.kodilla.CarRentalBackend.repository.DamageRepository;
import com.kodilla.CarRentalBackend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalOrderMapper {

    public final ReservationRepository reservationRepository;
    public final DamageRepository damageRepository;

    public RentalOrderDto mapToRentalOrderDto(final RentalOrder rentalOrder) {
        return new RentalOrderDto(
                rentalOrder.getId(),
                rentalOrder.getCost(),
                rentalOrder.getCostPaid(),
                rentalOrder.getOrderStatus(),
                rentalOrder.getFuelLevel(),
                rentalOrder.getDrivenKilometers(),
                rentalOrder.getReservation().getId(),
                Optional.ofNullable(rentalOrder.getDamage()).map(Damage::getId).orElse(null)
        );
    }

    public RentalOrder mapToRentalOrder(final RentalOrderDto rentalOrderDto) throws ReservationNotFoundException, DamageNotFoundException {
        Damage damage = null;
        if (rentalOrderDto.getDamageId() != null) {
            damage = damageRepository.findById(rentalOrderDto.getDamageId()).orElseThrow(DamageNotFoundException::new);
        }
        return new RentalOrder(
                rentalOrderDto.getId(),
                rentalOrderDto.getCost(),
                rentalOrderDto.getCostPaid(),
                rentalOrderDto.getOrderStatus(),
                rentalOrderDto.getFuelLevel(),
                rentalOrderDto.getDrivenKilometers(),
                reservationRepository.findById(rentalOrderDto.getReservationId()).orElseThrow(ReservationNotFoundException::new),
                damage
        );
    }

    public List<RentalOrderDto> mapToRentalOrderDtoList(final List<RentalOrder> rentalOrderList) {
        return rentalOrderList.stream()
                .map(this::mapToRentalOrderDto)
                .collect(Collectors.toList());
    }
}
