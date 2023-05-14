package com.kodilla.CarRentalBackend.controller;

import com.kodilla.CarRentalBackend.controller.exceptions.CarNotFoundException;
import com.kodilla.CarRentalBackend.controller.exceptions.DamageNotFoundExpection;
import com.kodilla.CarRentalBackend.domain.Damage;
import com.kodilla.CarRentalBackend.domain.Dto.DamageDto;
import com.kodilla.CarRentalBackend.mapper.DamageMapper;
import com.kodilla.CarRentalBackend.service.DamageDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/car_rental/damage")
@RequiredArgsConstructor
public class DamageController {

    private final DamageDbService damageDbService;
    private final DamageMapper damageMapper;


    @GetMapping
    public ResponseEntity<List<DamageDto>> getDamageList() {
        return ResponseEntity.ok(damageMapper.mapToDamageDtoList(damageDbService.getDamageList()));
    }

    @GetMapping(value = "{damageId}")
    public ResponseEntity<DamageDto> getDamage(@PathVariable Long damageId) throws DamageNotFoundExpection {
        return ResponseEntity.ok(damageMapper.mapToDamageDto(damageDbService.getDamageById(damageId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DamageDto> saveDamage(@RequestBody DamageDto damageDto) throws CarNotFoundException {
        Damage damage = damageMapper.mapToDamage(damageDto);
        damageDbService.saveDamage(damage);
        return ResponseEntity.ok(damageMapper.mapToDamageDto(damage));
    }

    @PutMapping(value = "{damageId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DamageDto> updateDamage(@PathVariable Long damageId, @RequestBody DamageDto damageDto) throws DamageNotFoundExpection, CarNotFoundException {
        Damage updatedDamage = damageDbService.updateDamage(damageId, damageDto);
        DamageDto updatedDamageDto = damageMapper.mapToDamageDto(updatedDamage);
        return ResponseEntity.ok(updatedDamageDto);
    }
}
