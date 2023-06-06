package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.domain.Damage;
import com.kodilla.CarRentalBackend.domain.Dto.RentalOrderDto;
import com.kodilla.CarRentalBackend.domain.OrderStatus;
import com.kodilla.CarRentalBackend.domain.RentalOrder;
import com.kodilla.CarRentalBackend.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RentalOrderMapperTest {

    @InjectMocks
    private RentalOrderMapper rentalOrderMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldMapRentalOrderToRentalOrderDto() {
        // Given
        RentalOrder rentalOrder = new RentalOrder();
        rentalOrder.setId(1L);
        rentalOrder.setCost(100.0);
        rentalOrder.setCostPaid(50.0);
        rentalOrder.setOrderStatus(OrderStatus.ADVANCE_PAYMENT);
        rentalOrder.setFuelLevel(0.5);
        rentalOrder.setDrivenKilometers(100L);
        rentalOrder.setReservation(new Reservation());
        rentalOrder.getReservation().setId(2L);
        rentalOrder.setDamage(new Damage());
        rentalOrder.getDamage().setId(3L);

        // When
        RentalOrderDto rentalOrderDto = rentalOrderMapper.mapToRentalOrderDto(rentalOrder);

        // Then
        assertEquals(rentalOrder.getId(), rentalOrderDto.getId());
        assertEquals(rentalOrder.getCost(), rentalOrderDto.getCost());
        assertEquals(rentalOrder.getCostPaid(), rentalOrderDto.getCostPaid());
        assertEquals(rentalOrder.getOrderStatus(), rentalOrderDto.getOrderStatus());
        assertEquals(rentalOrder.getFuelLevel(), rentalOrderDto.getFuelLevel());
        assertEquals(rentalOrder.getDrivenKilometers(), rentalOrderDto.getDrivenKilometers());
        assertEquals(rentalOrder.getReservation().getId(), rentalOrderDto.getReservationId());
        assertEquals(rentalOrder.getDamage().getId(), rentalOrderDto.getDamageId());
    }

    @Test
    public void shouldMapRentalOrderListToRentalOrderDtoList() {
        // Given
        List<RentalOrder> rentalOrderList = new ArrayList<>();
        RentalOrder rentalOrder1 = new RentalOrder();
        rentalOrder1.setId(1L);
        rentalOrder1.setCost(100.0);
        rentalOrder1.setCostPaid(100.0);
        rentalOrder1.setOrderStatus(OrderStatus.PAID);
        rentalOrder1.setFuelLevel(0.5);
        rentalOrder1.setDrivenKilometers(100L);
        rentalOrder1.setReservation(new Reservation());
        rentalOrder1.getReservation().setId(2L);
        rentalOrder1.setDamage(new Damage());
        rentalOrder1.getDamage().setId(3L);
        rentalOrderList.add(rentalOrder1);

        RentalOrder rentalOrder2 = new RentalOrder();
        rentalOrder2.setId(4L);
        rentalOrder2.setCost(200.0);
        rentalOrder2.setCostPaid(200.0);
        rentalOrder2.setOrderStatus(OrderStatus.PAID);
        rentalOrder2.setFuelLevel(0.75);
        rentalOrder2.setDrivenKilometers(200L);
        rentalOrder2.setReservation(new Reservation());
        rentalOrder2.getReservation().setId(5L);
        rentalOrder2.setDamage(null);
        rentalOrderList.add(rentalOrder2);

        // When
        List<RentalOrderDto> rentalOrderDtoList = rentalOrderMapper.mapToRentalOrderDtoList(rentalOrderList);

        // Then
        assertEquals(rentalOrderList.size(), rentalOrderDtoList.size());

        for (int i = 0; i < rentalOrderList.size(); i++) {
            RentalOrder rentalOrder = rentalOrderList.get(i);
            RentalOrderDto rentalOrderDto = rentalOrderDtoList.get(i);

            assertEquals(rentalOrder.getId(), rentalOrderDto.getId());
            assertEquals(rentalOrder.getCost(), rentalOrderDto.getCost());
            assertEquals(rentalOrder.getCostPaid(), rentalOrderDto.getCostPaid());
            assertEquals(rentalOrder.getOrderStatus(), rentalOrderDto.getOrderStatus());
            assertEquals(rentalOrder.getFuelLevel(), rentalOrderDto.getFuelLevel());
            assertEquals(rentalOrder.getDrivenKilometers(), rentalOrderDto.getDrivenKilometers());
            assertEquals(rentalOrder.getReservation().getId(), rentalOrderDto.getReservationId());
            assertEquals(rentalOrder.getDamage() != null ? rentalOrder.getDamage().getId() : null, rentalOrderDto.getDamageId());
        }
    }
}

