package com.example.flight_routing_system.service.impl;

import com.example.flight_routing_system.dto.RouteDto;
import com.example.flight_routing_system.model.Location;
import com.example.flight_routing_system.model.Transportation;
import com.example.flight_routing_system.model.TransportationType;
import com.example.flight_routing_system.repository.LocationRepository;
import com.example.flight_routing_system.repository.TransportationRepository;
import com.example.flight_routing_system.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final LocationRepository locationRepository;
    private final TransportationRepository transportationRepository;

    public RouteServiceImpl(LocationRepository locationRepository, TransportationRepository transportationRepository) {
        this.locationRepository = locationRepository;
        this.transportationRepository = transportationRepository;
    }

    @Override
    public List<RouteDto> findRoutes(String originCode, String destinationCode) {
        Location origin = locationRepository.findAll().stream()
                .filter(loc -> loc.getLocationCode().equalsIgnoreCase(originCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Origin not found"));

        Location destination = locationRepository.findAll().stream()
                .filter(loc -> loc.getLocationCode().equalsIgnoreCase(destinationCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        List<Transportation> all = transportationRepository.findAll();
        List<RouteDto> results = new ArrayList<>();

        // tüm 1li, 2li, 3lü kombinasyonları dene
        for (Transportation t1 : all) {
            if (!t1.getOrigin().equals(origin)) continue;

            // 1 aşamalı kontrol
            if (t1.getDestination().equals(destination)) {
                if (isValidRoute(List.of(t1))) results.add(new RouteDto(List.of(t1)));
            }

            for (Transportation t2 : all) {
                if (!t1.getDestination().equals(t2.getOrigin())) continue;

                // 2 aşamalı kontrol
                if (t2.getDestination().equals(destination)) {
                    if (isValidRoute(List.of(t1, t2))) results.add(new RouteDto(List.of(t1, t2)));
                }

                for (Transportation t3 : all) {
                    if (!t2.getDestination().equals(t3.getOrigin())) continue;
                    if (!t3.getDestination().equals(destination)) continue;

                    if (isValidRoute(List.of(t1, t2, t3))) {
                        results.add(new RouteDto(List.of(t1, t2, t3)));
                    }
                }
            }
        }

        return results;
    }

    private boolean isValidRoute(List<Transportation> route) {
        if (route.size() > 3) return false;

        long flightCount = route.stream().filter(t -> t.getType() == TransportationType.FLIGHT).count();
        if (flightCount != 1) return false;

        long beforeFlight = 0;
        long afterFlight = 0;
        boolean flightSeen = false;

        for (Transportation t : route) {
            if (t.getType() == TransportationType.FLIGHT) {
                if (flightSeen) return false; // birden fazla FLIGHT varsa
                flightSeen = true;
            } else {
                if (!flightSeen) beforeFlight++;
                else afterFlight++;
            }
        }

        return beforeFlight <= 1 && afterFlight <= 1;
    }
}
