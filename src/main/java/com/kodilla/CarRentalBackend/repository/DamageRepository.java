package com.kodilla.CarRentalBackend.repository;

import com.kodilla.CarRentalBackend.domain.Damage;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DamageRepository extends CrudRepository<Damage, Long> {

    List<Damage> findAll();
    Optional<Damage> findById(Long id);
}
