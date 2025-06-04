package com.example.flight_routing_system.controller;

import com.example.flight_routing_system.model.Location;
import com.example.flight_routing_system.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllLocations() throws Exception {
        Location loc1 = new Location(1L, "Airport A", "TR", "Istanbul", "AAA");
        Location loc2 = new Location(2L, "Airport B", "TR", "Ankara", "BBB");

        Mockito.when(locationService.getAll()).thenReturn(Arrays.asList(loc1, loc2));

        mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateLocation() throws Exception {
        Location newLoc = new Location(null, "New", "TR", "City", "XYZ");

        Mockito.when(locationService.create(Mockito.any(Location.class)))
                .thenReturn(newLoc);

        mockMvc.perform(post("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newLoc)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateLocation() throws Exception {
        Long id = 1L;
        Location updatedLoc = new Location(id, "Updated Name", "TR", "Istanbul", "AAA");

        Mockito.when(locationService.update(Mockito.eq(id), Mockito.any(Location.class)))
                .thenReturn(updatedLoc);

        mockMvc.perform(put("/api/locations/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedLoc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void testDeleteLocation() throws Exception {
        Long id = 1L;

        Mockito.doNothing().when(locationService).delete(id);

        mockMvc.perform(delete("/api/locations/{id}", id))
                .andExpect(status().isOk());
    }

}
