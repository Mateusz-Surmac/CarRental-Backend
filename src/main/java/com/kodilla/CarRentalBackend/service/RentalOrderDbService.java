package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.CarClass;
import com.kodilla.CarRentalBackend.domain.Dto.RentalOrderDto;
import com.kodilla.CarRentalBackend.domain.OrderStatus;
import com.kodilla.CarRentalBackend.domain.RentalOrder;
import com.kodilla.CarRentalBackend.exceptions.*;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import com.kodilla.CarRentalBackend.repository.DamageRepository;
import com.kodilla.CarRentalBackend.repository.RentalOrderRepository;
import com.kodilla.CarRentalBackend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalOrderDbService {

    private final RentalOrderRepository rentalOrderRepository;
    private final ReservationRepository reservationRepository;
    private final DamageRepository damageRepository;
    private final CarRepository carRepository;

    public List<RentalOrder> getRentalOrderList() {
        return rentalOrderRepository.findAll();
    }

    public RentalOrder getRentalOrderById(final Long id) throws RentalOrderNotFoundException {
        return rentalOrderRepository.findById(id).orElseThrow(RentalOrderNotFoundException::new);
    }

    public List<RentalOrder> getRentalOrdersByClientId(final Long clientId) {
        return rentalOrderRepository.findByReservation_Client_Id(clientId);
    }

    public List<RentalOrder> getRentalOrdersByCarId(final Long carId) {
        return rentalOrderRepository.findByReservation_Car_Id(carId);
    }

    public List<RentalOrder> getRentalOrdersByOrderStatus(final OrderStatus orderStatus) {
        return rentalOrderRepository.findByOrderStatus(orderStatus);
    }

    public double calculateAmountDueForClient(final Long clientId) {
        List<Double> costList = rentalOrderRepository.findCostByClientId(clientId);
        double totalCost = costList.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        List<Double> paidCostList = rentalOrderRepository.findPaidCostByClientId(clientId);
        double totalPaidCost = paidCostList.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        double cost = totalCost - totalPaidCost;
        return Math.round(cost * 100.0) / 100.0;
    }

    public RentalOrder saveRentalOrder(final RentalOrder rentalOrder) throws CarNotFoundException {
        rentalOrder.setCost(calculateCost(rentalOrder));

        Car car = carRepository.findById(rentalOrder.getReservation().getCar().getId()).orElseThrow(CarNotFoundException::new);
        car.setMileage(car.getMileage() + rentalOrder.getDrivenKilometers());
        carRepository.save(car);

        return rentalOrderRepository.save(rentalOrder);
    }

    public RentalOrder updateRentalOrder(final Long rentalOrderId, final RentalOrderDto rentalOrderDto) throws RentalOrderNotFoundException, ReservationNotFoundException, DamageNotFoundException, PaymentAmountExceededException, CarNotFoundException {
        RentalOrder rentalOrder = rentalOrderRepository.findById(rentalOrderId).orElseThrow(RentalOrderNotFoundException::new);
        rentalOrder.setFuelLevel(rentalOrderDto.getFuelLevel());
        rentalOrder.setDrivenKilometers(rentalOrderDto.getDrivenKilometers());
        rentalOrder.setReservation(reservationRepository.findById(rentalOrderDto.getReservationId()).orElseThrow(ReservationNotFoundException::new));

        if (rentalOrderDto.getDamageId() != null){
            rentalOrder.setDamage(damageRepository.findById(rentalOrderDto.getDamageId()).orElseThrow(DamageNotFoundException::new));
        }

        rentalOrder.setCost(calculateCost(rentalOrder));

        if (rentalOrder.getCostPaid() > rentalOrder.getCost()) {
            throw new PaymentAmountExceededException();
        }
        rentalOrder.setCostPaid(rentalOrderDto.getCostPaid());
        updateOrderStatus(rentalOrder);

        Car car = carRepository.findById(rentalOrder.getReservation().getCar().getId()).orElseThrow(CarNotFoundException::new);
        car.setMileage(car.getMileage() + rentalOrder.getDrivenKilometers());
        carRepository.save(car);

        return rentalOrderRepository.save(rentalOrder);
    }

    public double updateCostPaid(final Long rentalOrderId, double amount) throws RentalOrderNotFoundException, InvalidAmountException, PaymentAmountExceededException {
        RentalOrder rentalOrder = rentalOrderRepository.findById(rentalOrderId).orElseThrow(RentalOrderNotFoundException::new);

        amount +=rentalOrder.getCostPaid();

        if (amount < 0) {
            throw new InvalidAmountException();
        }

        if (amount > rentalOrder.getCost()) {
            throw new PaymentAmountExceededException();
        }

        rentalOrder.setCostPaid(amount);
        updateOrderStatus(rentalOrder);
        rentalOrderRepository.save(rentalOrder);

        return rentalOrder.getCost() - rentalOrder.getCostPaid();
    }

    public void deleteRentalOrder(final Long rentalOrderId) throws RentalOrderNotFoundException{
        if(!rentalOrderRepository.existsById(rentalOrderId)){
            throw new RentalOrderNotFoundException();
        }
        rentalOrderRepository.deleteById(rentalOrderId);
    }

    public double calculateCost(final RentalOrder rentalOrder) {
        double reservationPrice = rentalOrder.getReservation().getPrice();
        double damageCost = 0.0;

        if (rentalOrder.getDamage() != null) {
            damageCost = rentalOrder.getDamage().getCost();
        }

        double fuelRefillCost = calculateFuelRefillCost(rentalOrder.getReservation().getCar().getCarClass(),rentalOrder.getFuelLevel());
        double mileageCharge = rentalOrder.getReservation().getCar().getEngineCapacity() * 4 * (rentalOrder.getDrivenKilometers() * 0.01) * 8;
        double cost = reservationPrice + damageCost + mileageCharge + fuelRefillCost;

        return Math.round(cost * 100.0) / 100.0;
    }

    private void updateOrderStatus(final RentalOrder rentalOrder) {
        double costPaid = rentalOrder.getCostPaid();
        double price = rentalOrder.getCost();
        OrderStatus orderStatus;

        if (costPaid == price) {
            orderStatus = OrderStatus.PAID;
        } else if (costPaid > 0) {
            orderStatus = OrderStatus.ADVANCE_PAYMENT;
        } else {
            orderStatus = OrderStatus.UNPAID;
        }

        rentalOrder.setOrderStatus(orderStatus);
    }
    private double calculateFuelRefillCost(final CarClass carClass, double fuelLevel) {
        double tankCapacity = getTankCapacity(carClass);
        double missingFuel = tankCapacity * fuelLevel;

        if (missingFuel > 0) {
            double fuelRefillRate = 8;
            return missingFuel * fuelRefillRate;
        } else {
            return 0.0;
        }
    }

    private double getTankCapacity(final CarClass carClass) {
        return switch (carClass) {
            case ECONOMIC, VINTAGE -> 60.0;
            case LUXURY -> 80.0;
            case SPORTS -> 50.0;
            case TRANSPORT -> 100.0;
            default -> 70.0;
        };
    }
}
