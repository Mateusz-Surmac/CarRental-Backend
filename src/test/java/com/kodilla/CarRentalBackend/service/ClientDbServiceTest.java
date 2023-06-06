package com.kodilla.CarRentalBackend.service;

import com.kodilla.CarRentalBackend.exceptions.ClientNotFoundException;
import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Dto.ClientDto;
import com.kodilla.CarRentalBackend.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientDbServiceTest {

    private ClientDbService clientDbService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientDbService = new ClientDbService(clientRepository);
    }

    @Test
    void shouldReturnClientList() {
        // Given
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        // When
        List<Client> result = clientDbService.getClientList();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnClientById() throws ClientNotFoundException {
        // Given
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // When
        Client result = clientDbService.getClientById(clientId);

        // Then
        assertEquals(clientId, result.getId());
    }

    @Test
    void shouldReturnClientListByVipStatus() {
        // Given
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());
        when(clientRepository.findByVipStatus(true)).thenReturn(clients);

        // When
        List<Client> result = clientDbService.getClientListByVipStatus();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldSaveClient() {
        // Given
        Client client = new Client();
        when(clientRepository.save(client)).thenReturn(client);

        // When
        Client result = clientDbService.saveClient(client);

        // Then
        assertEquals(client, result);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void shouldUpdateVipStatus() throws ClientNotFoundException {
        // Given
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        // When
        Client result = clientDbService.updateVipStatus(clientId);

        // Then
        assertTrue(result.isVipStatus());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void shouldUpdateClient() throws ClientNotFoundException {
        // Given
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("John");
        clientDto.setLastName("Doe");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setVipStatus(true);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        // When
        Client result = clientDbService.updateClient(clientId, clientDto);

        // Then
        assertEquals(clientDto.getFirstName(), result.getFirstName());
        assertEquals(clientDto.getLastName(), result.getLastName());
        assertEquals(clientDto.getEmail(), result.getEmail());
        assertEquals(clientDto.isVipStatus(), result.isVipStatus());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void shouldDeleteClientById() throws ClientNotFoundException {
        // Given
        Long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(true);

        // When
        clientDbService.deleteClientById(clientId);

        // Then
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentClient() {
        // Given
        Long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(false);

        // When/Then
        assertThrows(ClientNotFoundException.class, () -> clientDbService.deleteClientById(clientId));
    }
}

