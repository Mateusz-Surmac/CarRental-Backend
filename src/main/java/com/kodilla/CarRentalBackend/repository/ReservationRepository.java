package com.kodilla.CarRentalBackend.repository;

import com.kodilla.CarRentalBackend.domain.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findAll();
    Optional<Reservation> findById(Long id);
    List<Reservation> findAllByCarId(Long carId);
    List<Reservation> findAllByClientId(Long clientId);
    boolean existsByCarIdAndRentalEndGreaterThanEqualAndRentalStartLessThanEqual(Long carId, LocalDate rentalStart, LocalDate rentalEnd);
    boolean existsByDriverIdAndRentalEndGreaterThanEqualAndRentalStartLessThanEqual(Long driverId, LocalDate rentalStart, LocalDate rentalEnd);
    List<Reservation> findByClientIdAndRentalStartBetween(Long clientId, LocalDate currentDate,LocalDate reminderDate);

}
