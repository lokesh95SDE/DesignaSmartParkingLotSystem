package org.ParkingLotSystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExitResponseDto(
        Long ticketId,
        LocalDateTime entryTime,
        LocalDateTime exitTime,
        long parkedMinutes,
        BigDecimal amount,
        String message
) {
}
