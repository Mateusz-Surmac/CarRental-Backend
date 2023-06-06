package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.domain.*;
import com.kodilla.CarRentalBackend.domain.Dto.RentalOrderDto;
import com.kodilla.CarRentalBackend.exceptions.*;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import com.kodilla.CarRentalBackend.repository.DamageRepository;
import com.kodilla.CarRentalBackend.repository.RentalOrderRepository;
import com.kodilla.CarRentalBackend.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RentalOrderDbServiceTest {

    private RentalOrderDbService rentalOrderDbService;

    @Mock
    private RentalOrderRepository rentalOrderRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private DamageRepository damageRepository;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rentalOrderDbService = new RentalOrderDbService(
                rentalOrderRepository, reservationRepository, damageRepository, carRepository);
    }

    @Test
    void shouldReturnRentalOrderList() {
        // Given
        List<RentalOrder> rentalOrders = new ArrayList<>();
        rentalOrders.add(new RentalOrder());
        rentalOrders.add(new RentalOrder());
        when(rentalOrderRepository.findAll()).thenReturn(rentalOrders);

        // When
        List<RentalOrder> result = rentalOrderDbService.getRentalOrderList();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnRentalOrderById() throws RentalOrderNotFoundException {
        // Given
        Long rentalOrderId = 1L;
        RentalOrder rentalOrder = new RentalOrder();
        rentalOrder.setId(rentalOrderId);
        when(rentalOrderRepository.findById(rentalOrderId)).thenReturn(Optional.of(rentalOrder));

        // When
        RentalOrder result = rentalOrderDbService.getRentalOrderById(rentalOrderId);

        // Then
        assertEquals(rentalOrderId, result.getId());
    }

    @Test
    void shouldReturnRentalOrdersByClientId() {
        // Given
        Long clientId = 1L;
        List<RentalOrder> rentalOrders = new ArrayList<>();
        rentalOrders.add(new RentalOrder());
        rentalOrders.add(new RentalOrder());
        when(rentalOrderRepository.findByReservation_Client_Id(clientId)).thenReturn(rentalOrders);

        // When
        List<RentalOrder> result = rentalOrderDbService.getRentalOrdersByClientId(clientId);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnRentalOrdersByCarId() {
        // Given
        Long carId = 1L;
        List<RentalOrder> rentalOrders = new ArrayList<>();
        rentalOrders.add(new RentalOrder());
        rentalOrders.add(new RentalOrder());
        when(rentalOrderRepository.findByReservation_Car_Id(carId)).thenReturn(rentalOrders);

        // When
        List<RentalOrder> result = rentalOrderDbService.getRentalOrdersByCarId(carId);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnRentalOrdersByOrderStatus() {
        // Given
        OrderStatus orderStatus = OrderStatus.PAID;
        List<RentalOrder> rentalOrders = new ArrayList<>();
        rentalOrders.add(new RentalOrder());
        rentalOrders.add(new RentalOrder());
        when(rentalOrderRepository.findByOrderStatus(orderStatus)).thenReturn(rentalOrders);

        // When
        List<RentalOrder> result = rentalOrderDbService.getRentalOrdersByOrderStatus(orderStatus);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldCalculateAmountDueForClient() {
        // Given
        Long clientId = 1L;
        List<Double> costList = new ArrayList<>();
        costList.add(100.0);
        costList.add(200.0);
        when(rentalOrderRepository.findCostByClientId(clientId)).thenReturn(costList);

        List<Double> paidCostList = new ArrayList<>();
        paidCostList.add(50.0);
        paidCostList.add(100.0);
        when(rentalOrderRepository.findPaidCostByClientId(clientId)).thenReturn(paidCostList);

        // When
        double result = rentalOrderDbService.calculateAmountDueForClient(clientId);

        // Then
        assertEquals(150.0, result);
    }

    @Test
    void shouldSaveRentalOrder() throws CarNotFoundException {
        // Given
        RentalOrder rentalOrder = new RentalOrder();
        rentalOrder.setReservation(new Reservation());
        Car car1 = new Car();
        car1.setMileage(1000L);
        car1.setCarClass(CarClass.ECONOMIC);
        rentalOrder.getReservation().setCar(car1);
        rentalOrder.getReservation().getCar().setId(1L);
        rentalOrder.setDrivenKilometers(100L);
        rentalOrder.setCost(200.0);
        when(rentalOrderRepository.save(rentalOrder)).thenReturn(rentalOrder);

        Car car = new Car();
        car.setId(1L);
        car.setMileage(1000L);
        car.setCarClass(CarClass.ECONOMIC);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        // When
        RentalOrder result = rentalOrderDbService.saveRentalOrder(rentalOrder);

        // Then
        assertEquals(rentalOrder, result);
        assertEquals(1100L, car.getMileage());
        verify(carRepository, times(1)).save(car);
        verify(rentalOrderRepository, times(1)).save(rentalOrder);
    }

    @Test
    void shouldUpdateRentalOrder() throws RentalOrderNotFoundException, ReservationNotFoundException, DamageNotFoundException, PaymentAmountExceededException, CarNotFoundException {
        // Given
        Long rentalOrderId = 1L;
        RentalOrderDto rentalOrderDto = new RentalOrderDto();
        rentalOrderDto.setFuelLevel(0.8);
        rentalOrderDto.setDrivenKilometers(100L);
        rentalOrderDto.setReservationId(1L);
        rentalOrderDto.setDamageId(1L);
        rentalOrderDto.setCostPaid(200.0);

        RentalOrder rentalOrder = new RentalOrder();
        rentalOrder.setFuelLevel(0.5);
        rentalOrder.setDrivenKilometers(50L);

        Reservation reservation = new Reservation();
        Car car = new Car();
        car.setCarClass(CarClass.ECONOMIC);
        car.setMileage(500L);
        reservation.setCar(car);
        reservation.getCar().setId(1L);

        Damage damage = new Damage();
        damage.setId(1L);
        rentalOrder.setDamage(damage);

        Car car2 = new Car();
        car2.setId(1L);
        car2.setMileage(500L);
        car2.setCarClass(CarClass.ECONOMIC);

        when(rentalOrderRepository.findById(rentalOrderId)).thenReturn(Optional.of(rentalOrder));
        when(reservationRepository.findById(rentalOrderDto.getReservationId())).thenReturn(Optional.of(reservation));
        when(damageRepository.findById(rentalOrderDto.getDamageId())).thenReturn(Optional.of(damage));
        when(carRepository.findById(reservation.getCar().getId())).thenReturn(Optional.of(car));
        when(rentalOrderRepository.save(rentalOrder)).thenReturn(rentalOrder);

        // When
        RentalOrder updatedRentalOrder = rentalOrderDbService.updateRentalOrder(rentalOrderId, rentalOrderDto);

        // Then
        assertEquals(rentalOrderDto.getFuelLevel(), updatedRentalOrder.getFuelLevel());
        assertEquals(rentalOrderDto.getDrivenKilometers(), updatedRentalOrder.getDrivenKilometers());
        assertEquals(rentalOrderDto.getDamageId(), updatedRentalOrder.getDamage().getId());
        assertEquals(rentalOrderDto.getCostPaid(), updatedRentalOrder.getCostPaid());
    }


    @Test
    void shouldThrowRentalOrderNotFoundExceptionWhenUpdatingNonExistingRentalOrder() {
        // Given
        Long rentalOrderId = 1L;
        RentalOrderDto rentalOrderDto = new RentalOrderDto();

        when(rentalOrderRepository.findById(rentalOrderId)).thenReturn(Optional.empty());

        // Then
        assertThrows(RentalOrderNotFoundException.class, () -> {
            rentalOrderDbService.updateRentalOrder(rentalOrderId, rentalOrderDto);
        });
    }

    @Test
    void shouldThrowReservationNotFoundExceptionWhenUpdatingRentalOrderWithNonExistingReservation() {
        // Given
        Long rentalOrderId = 1L;
        RentalOrder rentalOrder = new RentalOrder();
        rentalOrder.setId(rentalOrderId);
        RentalOrderDto rentalOrderDto = new RentalOrderDto();
        rentalOrderDto.setReservationId(2L);

        when(rentalOrderRepository.findById(rentalOrderId)).thenReturn(Optional.of(rentalOrder));
        when(reservationRepository.findById(2L)).thenReturn(Optional.empty());

        // Then
        assertThrows(ReservationNotFoundException.class, () -> {
            rentalOrderDbService.updateRentalOrder(rentalOrderId, rentalOrderDto);
        });
    }

    @Test
    void shouldDeleteRentalOrderById() throws RentalOrderNotFoundException {
        // Given
        Long rentalOrderId = 123L;
        when(rentalOrderRepository.existsById(rentalOrderId)).thenReturn(true);

        // When
        rentalOrderDbService.deleteRentalOrder(rentalOrderId);

        // Then
        verify(rentalOrderRepository, times(1)).deleteById(rentalOrderId);

    }


    @Test
    void shouldThrowRentalOrderNotFoundExceptionWhenDeletingNonExistingRentalOrder() {
        // Given
        Long rentalOrderId = 1L;
        when(rentalOrderRepository.findById(rentalOrderId)).thenReturn(Optional.empty());

        // Then
        assertThrows(RentalOrderNotFoundException.class, () -> {
            rentalOrderDbService.deleteRentalOrder(rentalOrderId);
        });
    }
}

