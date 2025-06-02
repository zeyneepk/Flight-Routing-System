package com.example.flight_routing_system.service;

import com.example.flight_routing_system.dto.TransportationDTO;
import com.example.flight_routing_system.model.Location;
import com.example.flight_routing_system.model.Transportation;
import com.example.flight_routing_system.model.TransportationType;
import com.example.flight_routing_system.repository.LocationRepository;
import com.example.flight_routing_system.repository.TransportationRepository;
import com.example.flight_routing_system.service.impl.TransportationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransportationServiceImplTest {

    private TransportationRepository transportationRepository;
    private LocationRepository locationRepository;
    private TransportationServiceImpl transportationService;

    @BeforeEach
    void setUp() {
        transportationRepository = mock(TransportationRepository.class);
        locationRepository = mock(LocationRepository.class);
        transportationService = new TransportationServiceImpl(transportationRepository, locationRepository);
    }

    @Test
    void testGetAll() {
        Transportation t1 = new Transportation();
        Transportation t2 = new Transportation();
        when(transportationRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<Transportation> list = transportationService.getAll();
        assertEquals(2, list.size());
        verify(transportationRepository, times(1)).findAll();
    }

    @Test
    void testUpdateFromDTO() {
        Transportation existing = new Transportation();
        existing.setId(1L);

        Location origin = new Location(1L, "Loc A", "TR", "City A", "LCA");
        Location destination = new Location(2L, "Loc B", "TR", "City B", "LCB");

        TransportationDTO dto = new TransportationDTO();
        dto.setOriginId(1L);
        dto.setDestinationId(2L);
        dto.setType("FLIGHT");

        when(transportationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(origin));
        when(locationRepository.findById(2L)).thenReturn(Optional.of(destination));
        when(transportationRepository.save(any(Transportation.class))).thenReturn(existing);

        Transportation result = transportationService.updateFromDTO(1L, dto);

        assertEquals(origin, result.getOrigin());
        assertEquals(destination, result.getDestination());
        assertEquals(TransportationType.FLIGHT, result.getType());
    }
}
