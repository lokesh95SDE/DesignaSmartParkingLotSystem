package org.ParkingLotSystem.dto;

import java.time.LocalDateTime;

public record EntryResponseDto(
        Long ticketId,
        LocalDateTime entryTime,
        String parkingSpotNumber,
        int floorNumber,
        String message
) {
}
