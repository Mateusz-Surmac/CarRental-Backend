package com.kodilla.CarRentalBackend.repository;

import com.kodilla.CarRentalBackend.domain.OrderStatus;
import com.kodilla.CarRentalBackend.domain.RentalOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RentalOrderRepository extends CrudRepository<RentalOrder, Long> {

    List<RentalOrder> findAll();
    Optional<RentalOrder> findById(Long id);
    List<RentalOrder> findByReservation_Client_Id(Long clientId);
    List<RentalOrder> findByReservation_Car_Id(Long carId);
    List<RentalOrder> findByOrderStatus(OrderStatus orderStatus);
    List<RentalOrder> findByReservationCarIdAndReservationRentalStartLessThanEqualAndReservationRentalEndGreaterThanEqual(
            Long carId, LocalDate rentalStart, LocalDate rentalEnd);

    @Query("SELECT ro.cost FROM RentalOrder ro JOIN ro.reservation r JOIN r.client c WHERE c.id = ?1")
    List<Double> findCostByClientId(Long clientId);

    @Query("SELECT ro.costPaid FROM RentalOrder ro JOIN ro.reservation r JOIN r.client c WHERE c.id = ?1")
    List<Double> findPaidCostByClientId(Long clientId);


}
