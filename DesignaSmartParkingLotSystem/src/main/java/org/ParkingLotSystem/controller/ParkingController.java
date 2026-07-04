package org.ParkingLotSystem.controller;

import jakarta.validation.Valid;
import org.ParkingLotSystem.dto.AvailabilityResponseDto;
import org.ParkingLotSystem.dto.EntryRequestDto;
import org.ParkingLotSystem.dto.EntryResponseDto;
import org.ParkingLotSystem.dto.ExitResponseDto;
import org.ParkingLotSystem.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/entry")
    public EntryResponseDto parkingEntry(@Valid @RequestBody EntryRequestDto entryRequestDto) {
        return parkingService.parkingEntry(entryRequestDto);
    }

    @PostMapping("/exit/{ticketId}")
    public ExitResponseDto parkingExit(@PathVariable Long ticketId) {
        return parkingService.parkingExit(ticketId);
    }

    @GetMapping("/availability")
    public List<AvailabilityResponseDto> getAvailableSpots() {
        return parkingService.getAvailableSpots();
    }
}
