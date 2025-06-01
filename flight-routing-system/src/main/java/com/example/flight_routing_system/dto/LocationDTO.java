
package com.example.flight_routing_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocationDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Location code is required")
    @Size(min = 3, max = 3, message = "Location code must be 3 characters")
    private String locationCode;
}
