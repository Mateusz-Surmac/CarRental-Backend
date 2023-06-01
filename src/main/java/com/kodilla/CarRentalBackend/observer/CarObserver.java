package com.kodilla.CarRentalBackend.observer;

import com.kodilla.CarRentalBackend.domain.Car;

public interface CarObserver {
    void updateCarDamage(Car car);
}