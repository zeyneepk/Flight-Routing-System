package com.example.flight_routing_system.service;

import com.example.flight_routing_system.model.Location;
import com.example.flight_routing_system.repository.LocationRepository;
import com.example.flight_routing_system.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceImplTest {

    private LocationRepository locationRepository;
    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class);
        locationService = new LocationServiceImpl(locationRepository);
    }

    @Test
    void testGetAll() {
        Location loc1 = new Location(1L, "Airport A", "TR", "Istanbul", "AAA");
        Location loc2 = new Location(2L, "Airport B", "TR", "Ankara", "BBB");

        List<Location> mockList = List.of(loc1, loc2);

        // Alfabetik sıralama
        when(locationRepository.findAll(Sort.by("name").ascending())).thenReturn(mockList);

        List<Location> result = locationService.getAll();

        assertEquals(2, result.size());
    }


    @Test
    void testGetById_Found() {
        Location loc = new Location(1L, "Airport A", "TR", "Istanbul", "AAA");
        when(locationRepository.findById(1L)).thenReturn(Optional.of(loc));

        Location result = locationService.getById(1L);

        assertEquals("Airport A", result.getName());
    }

    @Test
    void testCreate() {
        Location loc = new Location(null, "Airport X", "TR", "Izmir", "XYZ");
        when(locationRepository.save(loc)).thenReturn(loc);

        Location result = locationService.create(loc);

        assertNotNull(result);
        verify(locationRepository).save(loc);
    }
}
