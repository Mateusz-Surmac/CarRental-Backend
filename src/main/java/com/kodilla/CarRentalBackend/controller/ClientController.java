package com.kodilla.CarRentalBackend.controller;

import com.kodilla.CarRentalBackend.controller.exceptions.ClientNotFoundException;
import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Dto.ClientDto;
import com.kodilla.CarRentalBackend.mapper.ClientMapper;
import com.kodilla.CarRentalBackend.service.ClientDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/car_rental/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientDbService clientDbService;
    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClientList() {
        return ResponseEntity.ok(clientMapper.mapToClientDtoList(clientDbService.getClientList()));
    }

    @GetMapping(value = "{clientId}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long clientId) throws ClientNotFoundException {
        return ResponseEntity.ok(clientMapper.mapToClientDto(clientDbService.getClientById(clientId)));
    }

    @GetMapping(value = "vipClients")
    public ResponseEntity<List<ClientDto>> getVipStatusClients() {
        return ResponseEntity.ok(clientMapper.mapToClientDtoList(clientDbService.getClientListByVipStatus()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto){
        Client client = clientMapper.mapToClient(clientDto);
        clientDbService.saveClient(client);
        return ResponseEntity.ok(clientMapper.mapToClientDto(client));
    }

    @PutMapping(value = "updateVipStatus/{clientId}")
    public ResponseEntity<ClientDto> updateClientVipStatus(@PathVariable Long clientId) throws ClientNotFoundException {
        Client client = clientDbService.updateVipStatus(clientId);
        return ResponseEntity.ok(clientMapper.mapToClientDto(client));
    }

    @PutMapping(value = "{clientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long clientId, @RequestBody ClientDto clientDto) throws ClientNotFoundException {
        Client updatedClient = clientDbService.updateClient(clientId, clientDto);
        ClientDto updatedClientDto = clientMapper.mapToClientDto(updatedClient);
        return ResponseEntity.ok(updatedClientDto);
    }

    @DeleteMapping(value = "{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        clientDbService.deleteClientById(clientId);
        return ResponseEntity.ok().build();
    }
}
