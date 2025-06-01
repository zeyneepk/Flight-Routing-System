package com.example.flight_routing_system.service;

import com.example.flight_routing_system.model.Location;

import java.util.List;

public interface LocationService {
    List<Location> getAll();


    Location getById(Long id);
    Location create(Location location);
    Location update(Long id, Location location);
    void delete(Long id);
}
