package com.kodilla.CarRentalBackend.repository;

import com.kodilla.CarRentalBackend.domain.Driver;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DriverRepository extends CrudRepository<Driver, Long> {

    List<Driver> findAll();
    Optional<Driver> findById(Long id);
}
