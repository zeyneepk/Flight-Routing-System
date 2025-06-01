package com.example.flight_routing_system.controller;

import com.example.flight_routing_system.model.Location;
import com.example.flight_routing_system.service.LocationService;
import org.springframework.web.bind.annotation.*;
import com.example.flight_routing_system.dto.LocationDTO;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*") // React için CORS
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> getAll() {
        return locationService.getAll();
    }

    @GetMapping("/{id}")
    public Location getById(@PathVariable Long id) {
        return locationService.getById(id);
    }

    @PostMapping
    public Location create(@Valid @RequestBody LocationDTO locationDTO) {
        Location location = Location.builder()
                .name(locationDTO.getName())
                .country(locationDTO.getCountry())
                .city(locationDTO.getCity())
                .locationCode(locationDTO.getLocationCode())
                .build();

        return locationService.create(location);
    }


    @PutMapping("/{id}")
    public Location update(@PathVariable Long id, @RequestBody Location location) {
        return locationService.update(id, location);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locationService.delete(id);
    }


}