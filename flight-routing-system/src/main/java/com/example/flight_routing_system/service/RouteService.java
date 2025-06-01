package com.example.flight_routing_system.service;

import com.example.flight_routing_system.dto.RouteDto;

import java.util.List;

public interface RouteService {
    List<RouteDto> findRoutes(String originCode, String destinationCode);
}
