
package com.example.flight_routing_system.dto;

import lombok.Data;

@Data
public class TransportationDTO {
    private Long originId;
    private Long destinationId;
    private String type;
}
