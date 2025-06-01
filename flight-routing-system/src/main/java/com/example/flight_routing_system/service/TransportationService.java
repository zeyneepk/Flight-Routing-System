package com.example.flight_routing_system.service;

import com.example.flight_routing_system.dto.TransportationDTO;
import com.example.flight_routing_system.model.Transportation;

import java.util.List;

public interface TransportationService {
    List<Transportation> getAll();
    Transportation getById(Long id);
    Transportation create(Transportation t); // eski destek
    Transportation createFromDTO(TransportationDTO dto); // DTO destekli
    Transportation update(Long id, Transportation t); // eski destek
    Transportation updateFromDTO(Long id, TransportationDTO dto); // DTO destekli
    void delete(Long id);
}
