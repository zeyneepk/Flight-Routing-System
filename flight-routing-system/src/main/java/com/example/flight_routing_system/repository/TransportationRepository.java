package com.example.flight_routing_system.repository;

import com.example.flight_routing_system.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportationRepository extends JpaRepository<Transportation, Long> {
}
