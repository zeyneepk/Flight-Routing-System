package com.example.flight_routing_system.service.impl;

import com.example.flight_routing_system.model.Location;
import com.example.flight_routing_system.repository.LocationRepository;
import com.example.flight_routing_system.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location getById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));
    }

    @Override
    public Location create(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location update(Long id, Location newLoc) {
        Location loc = getById(id);
        loc.setName(newLoc.getName());
        loc.setCity(newLoc.getCity());
        loc.setCountry(newLoc.getCountry());
        loc.setLocationCode(newLoc.getLocationCode());
        return locationRepository.save(loc);
    }

    @Override
    public void delete(Long id) {
        locationRepository.deleteById(id);
    }
}
