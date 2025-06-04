
package com.example.flight_routing_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransportationDTO {

    @NotNull(message = "Origin location ID is required")
    private Long originId;

    @NotNull(message = "Destination location ID is required")
    private Long destinationId;

    @NotBlank(message = "Transportation type is required")
    private String type;


}
