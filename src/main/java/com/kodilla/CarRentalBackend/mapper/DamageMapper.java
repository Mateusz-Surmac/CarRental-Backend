package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.exceptions.CarNotFoundException;
import com.kodilla.CarRentalBackend.domain.Damage;
import com.kodilla.CarRentalBackend.domain.Dto.DamageDto;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DamageMapper {

    private final CarRepository carRepository;

    public DamageDto mapToDamageDto(Damage damage) {
        return new DamageDto(
                damage.getId(),
                damage.getDescription(),
                damage.getDate(),
                damage.getCost(),
                damage.getCar().getId()
        );
    }

    public Damage mapToDamage(DamageDto damageDto) throws CarNotFoundException{
        return new Damage(
                damageDto.getId(),
                damageDto.getDescription(),
                damageDto.getDate(),
                damageDto.getCost(),
                carRepository.findById(damageDto.getCarId()).orElseThrow(CarNotFoundException::new)
        );
    }

    public List<DamageDto> mapToDamageDtoList(final List<Damage> damageList) {
        return damageList.stream()
                .map(this::mapToDamageDto)
                .collect(Collectors.toList());
    }

}
