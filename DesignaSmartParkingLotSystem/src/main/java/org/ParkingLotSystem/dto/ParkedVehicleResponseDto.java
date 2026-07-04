package org.ParkingLotSystem.dto;

import org.ParkingLotSystem.enums.VehicleType;

public record ParkedVehicleResponseDto(
        Long ticketId,
        String registrationNumber,
        VehicleType vehicleType,
        String parkingSpotNumber,
        int floorNumber
) {
}
