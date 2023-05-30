package com.kodilla.CarRentalBackend.repository;


import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.CarClass;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findAll();
    Optional<Car> findById(Long id);
    List<Car> findAllByModel(String carModel);
    List<Car> findAllByCarClass(CarClass carClass);
    List<Car> findAllBySeatsNumberGreaterThanEqual(int seatsNumber);
    List<Car> findAllByManualGearbox(boolean manualGearbox);
    List<Car> findAllByProductionYearGreaterThanEqual(int productionYear);
    List<Car> findAllByMileageLessThanEqual(Long mileage);

}