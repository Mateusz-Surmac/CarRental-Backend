package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.controller.exceptions.CarNotFoundException;
import com.kodilla.CarRentalBackend.controller.exceptions.DamageNotFoundExpection;
import com.kodilla.CarRentalBackend.domain.Damage;
import com.kodilla.CarRentalBackend.domain.Dto.DamageDto;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import com.kodilla.CarRentalBackend.repository.DamageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DamageDbService {

    private final DamageRepository damageRepository;
    private final CarRepository carRepository;

    public List<Damage> getDamageList() {
        return damageRepository.findAll();
    }

    public Damage getDamageById(Long damageId) throws DamageNotFoundExpection {
        return damageRepository.findById(damageId).orElseThrow(DamageNotFoundExpection::new);
    }

    public Damage saveDamage(final Damage damage) {
        return damageRepository.save(damage);
    }

    public Damage updateDamage(Long damageId, DamageDto damageDto) throws DamageNotFoundExpection, CarNotFoundException{
        Damage damage = damageRepository.findById(damageId).orElseThrow(DamageNotFoundExpection::new);
        damage.setDescription(damageDto.getDescription());
        damage.setDate(damageDto.getDate());
        damage.setCost(damageDto.getCost());
        damage.setCar(carRepository.findById(damageId).orElseThrow(CarNotFoundException::new));
        return damageRepository.save(damage);
    }

}
