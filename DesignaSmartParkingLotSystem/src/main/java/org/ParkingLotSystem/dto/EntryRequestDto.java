package org.ParkingLotSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ParkingLotSystem.enums.VehicleType;

public record EntryRequestDto(
        @NotBlank(message = "Vehicle registration number is required")
        String vehicleRegistrationNumber,
        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType
) {
}
