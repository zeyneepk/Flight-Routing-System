
package com.example.flight_routing_system.controller;

import com.example.flight_routing_system.model.Transportation;
import com.example.flight_routing_system.service.TransportationService;
import com.example.flight_routing_system.dto.TransportationDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportations")
@CrossOrigin(origins = "*")
public class TransportationController {

    private final TransportationService transportationService;

    public TransportationController(TransportationService transportationService) {
        this.transportationService = transportationService;
    }

    @GetMapping
    public List<Transportation> getAll() {
        return transportationService.getAll();
    }

    @GetMapping("/{id}")
    public Transportation getById(@PathVariable Long id) {
        return transportationService.getById(id);
    }

    @PostMapping
    public Transportation create(@RequestBody TransportationDTO dto) {
        return transportationService.createFromDTO(dto);
    }

    @PutMapping("/{id}")
    public Transportation update(@PathVariable Long id, @RequestBody TransportationDTO dto) {
        return transportationService.updateFromDTO(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        transportationService.delete(id);
    }
}
