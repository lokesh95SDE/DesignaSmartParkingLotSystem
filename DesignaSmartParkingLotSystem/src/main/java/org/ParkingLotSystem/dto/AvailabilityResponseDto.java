package org.ParkingLotSystem.dto;

import org.ParkingLotSystem.enums.SpotType;

public record AvailabilityResponseDto(
        Long spotId,
        String spotNumber,
        SpotType spotType,
        String status,
        int floorNumber
) {
}
