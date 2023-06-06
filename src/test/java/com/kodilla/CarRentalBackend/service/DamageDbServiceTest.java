package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.Damage;
import com.kodilla.CarRentalBackend.domain.Dto.DamageDto;
import com.kodilla.CarRentalBackend.domain.Dto.RentalOrderDto;
import com.kodilla.CarRentalBackend.domain.RentalOrder;
import com.kodilla.CarRentalBackend.domain.Reservation;
import com.kodilla.CarRentalBackend.exceptions.*;
import com.kodilla.CarRentalBackend.mapper.CarMapper;
import com.kodilla.CarRentalBackend.mapper.RentalOrderMapper;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import com.kodilla.CarRentalBackend.repository.DamageRepository;
import com.kodilla.CarRentalBackend.repository.RentalOrderRepository;
import com.kodilla.CarRentalBackend.scheduler.EmailSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
class DamageDbServiceTest {

    private DamageDbService damageDbService;

    @Mock
    private DamageRepository damageRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private RentalOrderRepository rentalOrderRepository;

    @Mock
    private RentalOrderDbService rentalOrderDbService;

    @Mock
    private RentalOrderMapper rentalOrderMapper;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CarDbService carDbService;

    @Mock
    private CarMapper carMapper;

    @Mock
    private EmailSender emailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        damageDbService = new DamageDbService(
                damageRepository,
                carRepository,
                rentalOrderRepository,
                rentalOrderDbService,
                rentalOrderMapper,
                clientRepository,
                carDbService,
                carMapper,
                emailSender
        );
    }

    @Test
    void shouldReturnDamageList() {
        // Given
        List<Damage> damages = new ArrayList<>();
        damages.add(new Damage());
        damages.add(new Damage());
        when(damageRepository.findAll()).thenReturn(damages);

        // When
        List<Damage> result = damageDbService.getDamageList();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnDamageById() throws DamageNotFoundException {
        // Given
        Long damageId = 1L;
        Damage damage = new Damage();
        damage.setId(damageId);
        when(damageRepository.findById(damageId)).thenReturn(Optional.of(damage));

        // When
        Damage result = damageDbService.getDamageById(damageId);

        // Then
        assertEquals(damageId, result.getId());
    }

    @Test
    void shouldSaveDamage() throws CarNotFoundException, DamageNotFoundException, ReservationNotFoundException, RentalOrderNotFoundException, PaymentAmountExceededException {
        // Given
        Damage damage = new Damage();
        Car car = new Car();
        car.setId(1L);
        damage.setCar(car);
        when(carRepository.findById(damage.getCar().getId())).thenReturn(Optional.of(car));

        // When
        Damage result = damageDbService.saveDamage(damage);

        // Then
        assertEquals(damage, result);
        verify(damageRepository, times(1)).save(damage);
    }


    @Test
    void shouldUpdateRentalOrder() throws DamageNotFoundException, ReservationNotFoundException, CarNotFoundException, PaymentAmountExceededException, RentalOrderNotFoundException {
        // Given
        RentalOrderDbService rentalOrderDbService = mock(RentalOrderDbService.class);
        long rentalOrderId = 123;
        RentalOrderDto rentalOrderDto = new RentalOrderDto();

        // When
        rentalOrderDbService.updateRentalOrder(rentalOrderId, rentalOrderDto);

        // Then
        verify(rentalOrderDbService).updateRentalOrder(eq(rentalOrderId), eq(rentalOrderDto));
    }
}
