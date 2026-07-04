package org.ParkingLotSystem.controller;

import org.ParkingLotSystem.dto.ParkedVehicleResponseDto;
import org.ParkingLotSystem.service.ParkingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final ParkingService parkingService;

    public VehicleController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/parked")
    public List<ParkedVehicleResponseDto> vehiclesParked() {
        return parkingService.getParkedVehicles();
    }
}
