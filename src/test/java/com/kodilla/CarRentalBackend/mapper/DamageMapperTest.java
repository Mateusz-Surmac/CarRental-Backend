package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.Damage;
import com.kodilla.CarRentalBackend.domain.Dto.DamageDto;
import com.kodilla.CarRentalBackend.exceptions.CarNotFoundException;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DamageMapperTest {

    private DamageMapper damageMapper;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        damageMapper = new DamageMapper(carRepository);
    }

    @Test
    public void shouldMapDamageToDamageDto() {
        // Given
        Damage damage = new Damage();
        damage.setId(1L);
        damage.setDescription("Scratch on the door");
        damage.setDate(LocalDate.parse("2023-05-10"));
        damage.setCost(500.0);

        Car car = new Car();
        car.setId(1L);

        damage.setCar(car);

        // When
        DamageDto damageDto = damageMapper.mapToDamageDto(damage);

        // Then
        assertEquals(damage.getId(), damageDto.getId());
        assertEquals(damage.getDescription(), damageDto.getDescription());
        assertEquals(damage.getDate(), damageDto.getDate());
        assertEquals(damage.getCost(), damageDto.getCost());
        assertEquals(damage.getCar().getId(), damageDto.getCarId());
    }

    @Test
    public void shouldMapDamageDtoToDamage() throws CarNotFoundException {
        // Given
        DamageDto damageDto = new DamageDto();
        damageDto.setId(1L);
        damageDto.setDescription("Scratch on the door");
        damageDto.setDate(LocalDate.parse("2023-05-10"));
        damageDto.setCost(500.0);
        damageDto.setCarId(1L);

        Car car = new Car();
        car.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        // When
        Damage damage = damageMapper.mapToDamage(damageDto);

        // Then
        assertEquals(damageDto.getId(), damage.getId());
        assertEquals(damageDto.getDescription(), damage.getDescription());
        assertEquals(damageDto.getDate(), damage.getDate());
        assertEquals(damageDto.getCost(), damage.getCost());
        assertEquals(car, damage.getCar());
    }

    @Test
    public void shouldMapDamageListToDamageDtoList() {
        // Given
        List<Damage> damageList = new ArrayList<>();
        Damage damage1 = new Damage();
        damage1.setId(1L);
        damage1.setDescription("Scratch on the door");
        damage1.setDate(LocalDate.parse("2023-05-10"));
        damage1.setCost(500.0);

        Car car1 = new Car();
        car1.setId(1L);
        damage1.setCar(car1);

        damageList.add(damage1);

        Damage damage2 = new Damage();
        damage2.setId(2L);
        damage2.setDescription("Broken mirror");
        damage2.setDate(LocalDate.parse("2023-05-12"));
        damage2.setCost(300.0);

        Car car2 = new Car();
        car2.setId(2L);
        damage2.setCar(car2);

        damageList.add(damage2);

        // When
        List<DamageDto> damageDtoList = damageMapper.mapToDamageDtoList(damageList);

        // Then
        assertEquals(damageList.size(), damageDtoList.size());

        for (int i = 0; i < damageList.size(); i++) {
            Damage damage = damageList.get(i);
            DamageDto damageDto = damageDtoList.get(i);

            assertEquals(damage.getId(), damageDto.getId());
            assertEquals(damage.getDescription(), damageDto.getDescription());
            assertEquals(damage.getDate(), damageDto.getDate());
            assertEquals(damage.getCost(), damageDto.getCost());
            assertEquals(damage.getCar().getId(), damageDto.getCarId());
        }
    }
}
