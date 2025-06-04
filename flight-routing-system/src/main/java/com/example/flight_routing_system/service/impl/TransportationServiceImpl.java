
package com.example.flight_routing_system.service.impl;

import com.example.flight_routing_system.dto.TransportationDTO;
import com.example.flight_routing_system.model.Location;
import com.example.flight_routing_system.model.Transportation;
import com.example.flight_routing_system.model.TransportationType;
import com.example.flight_routing_system.repository.LocationRepository;
import com.example.flight_routing_system.repository.TransportationRepository;
import com.example.flight_routing_system.service.TransportationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportationServiceImpl implements TransportationService {

    private final TransportationRepository transportationRepository;
    private final LocationRepository locationRepository;

    public TransportationServiceImpl(TransportationRepository transportationRepository,
                                     LocationRepository locationRepository) {
        this.transportationRepository = transportationRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Transportation> getAll() {
        return transportationRepository.findAll();
    }

    @Override
    public Transportation getById(Long id) {
        return transportationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transportation not found"));
    }

    //@Override
    //public Transportation create(Transportation t) {
        //return null;
    //}

    @Override
    public Transportation createFromDTO(TransportationDTO dto) {
        if (dto.getOriginId() == null || dto.getDestinationId() == null) {
            throw new IllegalArgumentException("Origin and destination IDs must not be null");
        }

        Location origin = locationRepository.findById(dto.getOriginId())
                .orElseThrow(() -> new RuntimeException("Origin location not found"));
        Location destination = locationRepository.findById(dto.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination location not found"));

        Transportation t = Transportation.builder()
                .origin(origin)
                .destination(destination)
                .type(TransportationType.valueOf(dto.getType().toUpperCase()))
                .build();

        return transportationRepository.save(t);
    }


    //@Override
    //public Transportation update(Long id, Transportation t) {
    //    return null;
    //}

    @Override
    public Transportation updateFromDTO(Long id, TransportationDTO dto) {
        Transportation existing = getById(id);

        Location origin = locationRepository.findById(dto.getOriginId())
                .orElseThrow(() -> new RuntimeException("Origin location not found"));
        Location destination = locationRepository.findById(dto.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination location not found"));

        existing.setOrigin(origin);
        existing.setDestination(destination);
        existing.setType(TransportationType.valueOf(dto.getType().toUpperCase()));

        return transportationRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        transportationRepository.deleteById(id);
    }
}
