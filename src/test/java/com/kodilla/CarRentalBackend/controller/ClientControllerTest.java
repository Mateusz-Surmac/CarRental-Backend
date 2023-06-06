package com.kodilla.CarRentalBackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.CarRentalBackend.domain.Client;
import com.kodilla.CarRentalBackend.domain.Dto.ClientDto;
import com.kodilla.CarRentalBackend.mapper.ClientMapper;
import com.kodilla.CarRentalBackend.service.ClientDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientDbService clientDbService;

    @MockBean
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientController clientController;

    @Test
    public void getClientListTest() throws Exception {
        // Given
        Client client1 = new Client(1L, "Test - firstName", "Test - lastName", "test@example.com",false);
        Client client2 = new Client(2L, "Test2 - firstName", "Test2 - lastName", "Test2@example.com", false);
        List<Client> clients = Arrays.asList(client1, client2);

        ClientDto clientDto1 = new ClientDto(1L, "Test - firstName", "Test - lastName", "test@example.com", false);
        ClientDto clientDto2 = new ClientDto(2L, "Test2 - firstName", "Test2 - lastName", "Test2@example.com", false);
        List<ClientDto> clientDtos = Arrays.asList(clientDto1, clientDto2);

        Mockito.when(clientDbService.getClientList()).thenReturn(clients);
        Mockito.when(clientMapper.mapToClientDtoList(clients)).thenReturn(clientDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/client")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Test - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Test2 - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Test2 - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("Test2@example.com"));
    }

    @Test
    public void getClientTest() throws Exception {
        // Given
        Long clientId = 1L;
        Client client = new Client(clientId, "Test - firstName", "Test - lastName", "test@example.com", false);
        ClientDto clientDto = new ClientDto(clientId, "Test - firstName", "Test - lastName", "test@example.com", false);

        Mockito.when(clientDbService.getClientById(clientId)).thenReturn(client);
        Mockito.when(clientMapper.mapToClientDto(client)).thenReturn(clientDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/client/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(clientId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Test - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void getVipStatusClientsTest() throws Exception {
        // Given
        Client client1 = new Client(1L, "Test - firstName", "Test - lastName", "test@example.com", false);
        Client client2 = new Client(2L, "Test2 - firstName", "Test 2 - lastName", "test2@example.com", false);
        List<Client> clients = Arrays.asList(client1, client2);

        ClientDto clientDto1 = new ClientDto(1L, "Test - firstName", "Test - lastName", "test@example.com", false);
        ClientDto clientDto2 = new ClientDto(2L, "Test2 - firstName", "Test2 - lastName", "Test2@example.com", false);
        List<ClientDto> clientDtos = Arrays.asList(clientDto1, clientDto2);

        Mockito.when(clientDbService.getClientListByVipStatus()).thenReturn(clients);
        Mockito.when(clientMapper.mapToClientDtoList(clients)).thenReturn(clientDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/car_rental/client/vipClients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Test - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Test2 - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Test2 - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("Test2@example.com"));
    }

    @Test
    public void createClientTest() throws Exception {
        // Given
        ClientDto clientDto = new ClientDto(null, "Test - firstName", "Test - lastName", "test@example.com", false);
        Client client = new Client(null, "Test - firstName", "Test - lastName", "test@example.com", false);

        Mockito.when(clientMapper.mapToClient(clientDto)).thenReturn(client);
        Mockito.when(clientDbService.saveClient(client)).thenReturn(client);
        Mockito.when(clientMapper.mapToClientDto(client)).thenReturn(clientDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(clientDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/car_rental/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Test - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void updateClientVipStatusTest() throws Exception {
        // Given
        Long clientId = 1L;
        Client client = new Client(clientId, "Test - firstName", "Test - lastName", "test@example.com", false);

        Mockito.when(clientDbService.updateVipStatus(clientId)).thenReturn(client);
        Mockito.when(clientMapper.mapToClientDto(client)).thenReturn(new ClientDto());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/car_rental/client/updateVipStatus/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").doesNotExist());
    }

    @Test
    public void updateClientTest() throws Exception {
        // Given
        Long clientId = 1L;
        ClientDto clientDto = new ClientDto(clientId, "Test - firstName", "Test - lastName", "test@example.com", false);
        Client client = new Client(clientId, "Test - firstName", "Test - lastName", "test@example.com", false);

        Mockito.when(clientMapper.mapToClient(clientDto)).thenReturn(client);
        Mockito.when(clientDbService.updateClient(clientId, clientDto)).thenReturn(client);
        Mockito.when(clientMapper.mapToClientDto(client)).thenReturn(clientDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(clientDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/car_rental/client/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(clientId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test - firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Test - lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void deleteClientTest() throws Exception {
        // Given
        Long clientId = 1L;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/car_rental/client/{clientId}", clientId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(clientDbService, Mockito.times(1)).deleteClientById(clientId);
    }
}

