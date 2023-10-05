# Kodilla course - final project
CarRental-Frontend - https://github.com/Mateusz-Surmac/CarRental-Frontend

Web application that allows you to rent cars, book trips, and report damage.

## Features
- External API:
  - BingMaps - provides the distance between the car rental location and the car drop-off point
  - OpenWeather - provides the weather forecast on the day of car rental
- Email sender scheduler - reminds you about the upcoming reservation and payment, notifies you in the event of a collision in the car you plan to rent
- Design patterns:
    - Strategy
    - Observer
- Test coverage > 75%

## CarController 
* `GET` _car_ - Get a list of all cars.
* `GET` _car/{carId}_ - Get the details of a specific car by its ID.
* `GET` _car/model?carModel={carModel}_ - Get a list of cars by their model.
* `GET` _car/class?carClass={carClass}_ - Get a list of cars by their class.
* `GET` _car/seats?seatsNumber={seatsNumber}_ - Get a list of cars by their number of seats.
* `GET` _car/gearbox?manualGearbox={manualGearbox}_ - Get a list of cars by their gearbox type (manual or automatic).
* `GET` _car/year?productionYear={productionYear}_ - Get a list of cars by their production year.
* `GET` _car/mileage?mileage={mileage}_ - Get a list of cars by their mileage.
* `POST` _car_ - Create a new car.
* `PUT` _car/{carId}_ - Update the details of a specific car by its ID.
* `DELETE` _car/{carId}_ - Delete a car by its ID.

## ClientController
* `GET`	_client_ - Get a list of all clients.
* `GET`	_client/vipClients_ - 	Get a list of clients with VIP status.
* `GET`	_client/{clientId}_	- Get the details of a specific client by their ID.
* `POST`	_client_	- Create a new client.
* `PUT`	_client/{clientId}_	- Update the details of a client by their ID.
* `PUT`	_client/updateVipStatus/{clientId}_	- Update the VIP status of a client by their ID.
* `DELETE`	_client/{clientId}_	- Delete a client by their ID.

## DamageController
* `GET` _damage_ - Get a list of all damages.
* `GET` _damage/{damageId}_ - Get the details of a specific damage by its ID.
* `POST` _damage_ - Create a new damage.
* `PUT` _damage/{damageId}_ - Update the details of a damage by its ID.


## DriverController
* `GET` _driver_ - Get a list of all drivers. 
* `GET` _driver/{driverId}_ - Get the details of a specific driver by their ID.
* `GET` _driver/employeeDriverList_ - Get a list of drivers who are employees of the car rental company.
* `POST` _driver_ - Create a new driver.
* `PUT` _driver/{driverId}_ - Update the details of a driver by their ID.

## ReservationController
* `GET` _reservation_ - Get a list of all reservations.
* `GET` _reservation/{reservationId}_ - Get the details of a specific reservation by its ID.
* `GET` _reservation/client/{clientId}_ - Get a list of reservations by client ID.
* `GET` _reservation/car/{carId}_ - Get a list of reservations by car ID.
* `POST` _reservation_ - Create a new reservation.
* `PUT` _reservation/{reservationId}_ - Update the details of a specific reservation by its ID.
* `DELETE` _reservation/{reservationId}_ - Delete a reservation by its ID.

## RentalOrderController
* `GET` _rental_order_ - Get a list of all rental orders.
* `GET` _rental_order/{rentalOrderId}_ - Get the details of a specific rental order by its ID.
* `GET` _rental_order/client/{clientId}_ - Get a list of rental orders by client ID.
* `GET` _rental_order/car/{carId}_ - Get a list of rental orders by car ID.
* `GET` _rental_order/status/{orderStatus}_ - Get a list of rental orders by order status.
* `GET` _rental_order/client/{clientId}/amountDue_ - Calculate the total amount due for a client.
* `POST` _rental_order_ - Create a new rental order.
* `PUT` _rental_order/{rentalOrderId}_ - Update the details of a specific rental order by its ID.
* `PUT` _rental_order/{rentalOrderId}/costPaid/{amount}_ - Update the amount paid for a rental order by its ID.
* `DELETE` _rental_order/{rentalOrderId}_ - Delete a rental order by its ID.
