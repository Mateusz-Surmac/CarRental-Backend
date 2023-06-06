package com.kodilla.CarRentalBackend.mapper;

import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Dto.ClientDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClientMapperTest {

    private final ClientMapper clientMapper = new ClientMapper();

    @Test
    public void shouldMapClientToClientDto() {
        // Given
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");
        client.setVipStatus(true);

        // When
        ClientDto clientDto = clientMapper.mapToClientDto(client);

        // Then
        assertEquals(client.getId(), clientDto.getId());
        assertEquals(client.getFirstName(), clientDto.getFirstName());
        assertEquals(client.getLastName(), clientDto.getLastName());
        assertEquals(client.getEmail(), clientDto.getEmail());
        assertEquals(client.isVipStatus(), clientDto.isVipStatus());
    }

    @Test
    public void shouldMapClientDtoToClient() {
        // Given
        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setFirstName("John");
        clientDto.setLastName("Doe");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setVipStatus(true);

        // When
        Client client = clientMapper.mapToClient(clientDto);

        // Then
        assertEquals(clientDto.getId(), client.getId());
        assertEquals(clientDto.getFirstName(), client.getFirstName());
        assertEquals(clientDto.getLastName(), client.getLastName());
        assertEquals(clientDto.getEmail(), client.getEmail());
        assertEquals(clientDto.isVipStatus(), client.isVipStatus());
    }

    @Test
    public void shouldMapClientListToClientDtoList() {
        // Given
        List<Client> clientList = new ArrayList<>();
        Client client1 = new Client();
        client1.setId(1L);
        client1.setFirstName("John");
        client1.setLastName("Doe");
        client1.setEmail("john.doe@example.com");
        client1.setVipStatus(true);
        clientList.add(client1);

        Client client2 = new Client();
        client2.setId(2L);
        client2.setFirstName("Jane");
        client2.setLastName("Smith");
        client2.setEmail("jane.smith@example.com");
        client2.setVipStatus(false);
        clientList.add(client2);

        // When
        List<ClientDto> clientDtoList = clientMapper.mapToClientDtoList(clientList);

        // Then
        assertEquals(clientList.size(), clientDtoList.size());

        for (int i = 0; i < clientList.size(); i++) {
            Client client = clientList.get(i);
            ClientDto clientDto = clientDtoList.get(i);

            assertEquals(client.getId(), clientDto.getId());
            assertEquals(client.getFirstName(), clientDto.getFirstName());
            assertEquals(client.getLastName(), clientDto.getLastName());
            assertEquals(client.getEmail(), clientDto.getEmail());
            assertEquals(client.isVipStatus(), clientDto.isVipStatus());
        }
    }
}
