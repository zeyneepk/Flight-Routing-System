package com.example.flight_routing_system.controller;

import com.example.flight_routing_system.dto.RouteDto;
import com.example.flight_routing_system.service.RouteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<RouteDto> getRoutes(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        return routeService.findRoutes(origin, destination);
    }
}
