package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.exceptions.ClientNotFoundException;
import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Dto.ClientDto;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientDbService {

    private final ClientRepository clientRepository;

    public List<Client> getClientList() {
        return clientRepository.findAll();
    }

    public Client getClientById(final Long clientId) throws ClientNotFoundException {
        return clientRepository.findById(clientId).orElseThrow(ClientNotFoundException::new);
    }

    public List<Client> getClientListByVipStatus() {
        return clientRepository.findByVipStatus(true);
    }

    public Client saveClient(final Client client) {
        return clientRepository.save(client);
    }

    public Client updateVipStatus(final Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElseThrow(ClientNotFoundException::new);
        client.setVipStatus(true);
        return clientRepository.save(client);
    }

    public Client updateClient(final Long clientId, final ClientDto clientDto) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElseThrow(ClientNotFoundException::new);
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setVipStatus(clientDto.isVipStatus());
        return clientRepository.save(client);
    }

    public void deleteClientById(final Long clientId) throws ClientNotFoundException{
        if (!clientRepository.existsById(clientId)) {
            throw new ClientNotFoundException();
        }
        clientRepository.deleteById(clientId);
    }
}
