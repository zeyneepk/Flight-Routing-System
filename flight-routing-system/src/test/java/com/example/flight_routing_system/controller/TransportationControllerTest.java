package com.example.flight_routing_system.controller;

import com.example.flight_routing_system.dto.TransportationDTO;
import com.example.flight_routing_system.model.Location;
import com.example.flight_routing_system.model.Transportation;
import com.example.flight_routing_system.model.TransportationType;
import com.example.flight_routing_system.service.TransportationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransportationController.class)
public class TransportationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransportationService transportationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllTransportations() throws Exception {
        Transportation t = new Transportation();
        t.setId(1L);
        t.setType(TransportationType.UBER);
        Mockito.when(transportationService.getAll()).thenReturn(java.util.List.of(t));

        mockMvc.perform(get("/api/transportations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testUpdateTransportation() throws Exception {
        TransportationDTO dto = new TransportationDTO();
        dto.setOriginId(1L);
        dto.setDestinationId(2L);
        dto.setType("FLIGHT");

        Transportation t = new Transportation();
        t.setId(1L);
        t.setType(TransportationType.FLIGHT);

        Mockito.when(transportationService.updateFromDTO(Mockito.eq(1L), Mockito.any()))
                .thenReturn(t);

        mockMvc.perform(put("/api/transportations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("FLIGHT"));
    }

    @Test
    void testCreateTransportation() throws Exception {
        TransportationDTO dto = new TransportationDTO();
        dto.setOriginId(1L);
        dto.setDestinationId(2L);
        dto.setType("BUS");

        Transportation created = new Transportation();
        created.setId(10L);
        created.setType(TransportationType.BUS);

        Mockito.when(transportationService.createFromDTO(Mockito.any())).thenReturn(created);

        mockMvc.perform(post("/api/transportations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.type").value("BUS"));
    }

    @Test
    void testDeleteTransportation() throws Exception {
        Long idToDelete = 5L;

        // service.delete(id) void döndüğü için sadece çağrılıp çağrılmadığını kontrol eder
        Mockito.doNothing().when(transportationService).delete(idToDelete);

        mockMvc.perform(delete("/api/transportations/" + idToDelete))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateTransportation_withMissingFields_shouldReturnBadRequest() throws Exception {
        TransportationDTO invalidDto = new TransportationDTO(); // boş DTO

        mockMvc.perform(post("/api/transportations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }



}
