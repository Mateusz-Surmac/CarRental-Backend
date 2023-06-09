package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.domain.Car;
import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Damage;
import com.kodilla.CarRentalBackend.domain.Dto.DamageDto;
import com.kodilla.CarRentalBackend.domain.RentalOrder;
import com.kodilla.CarRentalBackend.exceptions.*;
import com.kodilla.CarRentalBackend.mapper.CarMapper;
import com.kodilla.CarRentalBackend.mapper.RentalOrderMapper;
import com.kodilla.CarRentalBackend.observer.ClientCarObserver;
import com.kodilla.CarRentalBackend.repository.CarRepository;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import com.kodilla.CarRentalBackend.repository.DamageRepository;
import com.kodilla.CarRentalBackend.repository.RentalOrderRepository;
import com.kodilla.CarRentalBackend.scheduler.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DamageDbService {

    private final DamageRepository damageRepository;
    private final CarRepository carRepository;
    private final RentalOrderRepository rentalOrderRepository;
    private final RentalOrderDbService rentalOrderDbService;
    private final RentalOrderMapper rentalOrderMapper;
    private final ClientRepository clientRepository;
    private final CarDbService carDbService;
    private final CarMapper carMapper;
    private final EmailSender emailSender;

    public List<Damage> getDamageList() {
        return damageRepository.findAll();
    }

    public Damage getDamageById(final Long damageId) throws DamageNotFoundException {
        return damageRepository.findById(damageId).orElseThrow(DamageNotFoundException::new);
    }

    public Damage saveDamage(final Damage damage) throws CarNotFoundException, DamageNotFoundException, ReservationNotFoundException, RentalOrderNotFoundException, PaymentAmountExceededException {
        Car car = carRepository.findById(damage.getCar().getId()).orElseThrow(CarNotFoundException::new);
        setDamageAndNotifyObservers(car);

        damageRepository.save(damage);
        assignDamageToRentalOrderAndUpdateCost(damage);

        return damage;
    }

    public Damage updateDamage(final Long damageId,final  DamageDto damageDto) throws DamageNotFoundException, CarNotFoundException, ReservationNotFoundException, PaymentAmountExceededException, RentalOrderNotFoundException {
        Damage damage = damageRepository.findById(damageId).orElseThrow(DamageNotFoundException::new);
        damage.setDescription(damageDto.getDescription());
        damage.setDate(damageDto.getDate());
        damage.setCost(damageDto.getCost());

        Car car = carRepository.findById(damage.getCar().getId()).orElseThrow(CarNotFoundException::new);
        damage.setCar(car);

        damageRepository.save(damage);
        assignDamageToRentalOrderAndUpdateCost(damage);

        return damage;
    }

    private void assignDamageToRentalOrderAndUpdateCost(final Damage damage) throws DamageNotFoundException, ReservationNotFoundException, RentalOrderNotFoundException, PaymentAmountExceededException, CarNotFoundException {
        List<RentalOrder> rentalOrders = rentalOrderRepository.findByReservationCarIdAndReservationRentalStartLessThanEqualAndReservationRentalEndGreaterThanEqual(
                damage.getCar().getId(), damage.getDate(), damage.getDate());

        for (RentalOrder rentalOrder : rentalOrders) {
            if (rentalOrder.getReservation().getCar().getId().equals(damage.getCar().getId())) {
                rentalOrder.setDamage(damage);
                rentalOrderDbService.updateRentalOrder(rentalOrder.getId(), rentalOrderMapper.mapToRentalOrderDto(rentalOrder));
                break;
            }
        }
    }

    private void setDamageAndNotifyObservers(Car car) throws CarNotFoundException {
        car.setDamaged(true);
        carDbService.updateCar(car.getId(),carMapper.mapToCarDto(car));
        List<Client> clients = clientRepository.findByReservationsCarIdAndReservationsRentalEndGreaterThanEqual(car.getId(), LocalDate.now());
        List<ClientCarObserver> observers = new ArrayList<>();

        for (Client client : clients) {
            ClientCarObserver observer = new ClientCarObserver(client,emailSender);
            observers.add(observer);
        }

        car.setObservers(observers);
        car.notifyObservers();
    }

}
