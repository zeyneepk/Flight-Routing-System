package com.example.flight_routing_system.dto;

import com.example.flight_routing_system.model.Transportation;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDto {
    private List<Transportation> steps; // rota adımları
}
