package com.kodilla.CarRentalBackend.controller;

import com.kodilla.CarRentalBackend.domain.Dto.RentalOrderDto;
import com.kodilla.CarRentalBackend.domain.OrderStatus;
import com.kodilla.CarRentalBackend.domain.RentalOrder;
import com.kodilla.CarRentalBackend.exceptions.*;
import com.kodilla.CarRentalBackend.mapper.RentalOrderMapper;
import com.kodilla.CarRentalBackend.service.RentalOrderDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/car_rental/rental_order")
@RequiredArgsConstructor
public class RentalOrderController {

    private final RentalOrderDbService rentalOrderDbService;
    private final RentalOrderMapper rentalOrderMapper;

    @GetMapping
    public ResponseEntity<List<RentalOrderDto>> getRentalOrderList() {
        return ResponseEntity.ok(rentalOrderMapper.mapToRentalOrderDtoList(rentalOrderDbService.getRentalOrderList()));
    }

    @GetMapping(value = "{rentalOrderId}")
    public ResponseEntity<RentalOrderDto> getRentalOrder(@PathVariable Long rentalOrderId) throws RentalOrderNotFoundException {
        return ResponseEntity.ok(rentalOrderMapper.mapToRentalOrderDto(rentalOrderDbService.getRentalOrderById(rentalOrderId)));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<RentalOrderDto>> getRentalOrdersByClientId(@PathVariable Long clientId) {
        List<RentalOrder> rentalOrders = rentalOrderDbService.getRentalOrdersByClientId(clientId);
        List<RentalOrderDto> rentalOrderDtoList = rentalOrderMapper.mapToRentalOrderDtoList(rentalOrders);
        return ResponseEntity.ok(rentalOrderDtoList);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<RentalOrderDto>> getRentalOrdersByCarId(@PathVariable Long carId) {
        List<RentalOrder> rentalOrders = rentalOrderDbService.getRentalOrdersByCarId(carId);
        List<RentalOrderDto> rentalOrderDtoList = rentalOrderMapper.mapToRentalOrderDtoList(rentalOrders);
        return ResponseEntity.ok(rentalOrderDtoList);
    }

    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<List<RentalOrderDto>> getRentalOrdersByOrderStatus(@PathVariable OrderStatus orderStatus) {
        List<RentalOrder> rentalOrders = rentalOrderDbService.getRentalOrdersByOrderStatus(orderStatus);
        List<RentalOrderDto> rentalOrderDtoList = rentalOrderMapper.mapToRentalOrderDtoList(rentalOrders);
        return ResponseEntity.ok(rentalOrderDtoList);
    }

    @GetMapping("/client/{clientId}/amountDue")
    public ResponseEntity<Double> calculateAmountDueForClient(@PathVariable Long clientId) {
        double amountDue = rentalOrderDbService.calculateAmountDueForClient(clientId);
        return ResponseEntity.ok(amountDue);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalOrderDto> createRentalOrder(@RequestBody RentalOrderDto rentalOrderDto) throws CarNotFoundException, ReservationNotFoundException, DamageNotFoundException {
        RentalOrder rentalOrder = rentalOrderMapper.mapToRentalOrder(rentalOrderDto);
        rentalOrderDbService.saveRentalOrder(rentalOrder);
        return ResponseEntity.ok(rentalOrderMapper.mapToRentalOrderDto(rentalOrder));
    }

    @PutMapping(value = "{rentalOrderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalOrderDto> updateRentalOrder(@PathVariable Long rentalOrderId, @RequestBody RentalOrderDto rentalOrderDto) throws RentalOrderNotFoundException, ReservationNotFoundException, DamageNotFoundException, PaymentAmountExceededException, CarNotFoundException {
        RentalOrder updatedRentalOrder = rentalOrderDbService.updateRentalOrder(rentalOrderId, rentalOrderDto);
        RentalOrderDto updatedRentalOrderDto = rentalOrderMapper.mapToRentalOrderDto(updatedRentalOrder);
        return ResponseEntity.ok(updatedRentalOrderDto);
    }

    @PutMapping(value = "/{rentalOrderId}/costPaid/{amount}")
    public ResponseEntity<Double> updateCostPaid(@PathVariable Long rentalOrderId, @PathVariable double amount) throws InvalidAmountException, PaymentAmountExceededException, RentalOrderNotFoundException {

        double remainingAmount = rentalOrderDbService.updateCostPaid(rentalOrderId, amount);
        return ResponseEntity.ok(remainingAmount);
    }

    @DeleteMapping(value = "{rentalOrderId}")
    public ResponseEntity<Void> deleteRentalOrder(@PathVariable Long rentalOrderId) throws RentalOrderNotFoundException {
        rentalOrderDbService.deleteRentalOrder(rentalOrderId);
       return ResponseEntity.ok().build();
    }
}
