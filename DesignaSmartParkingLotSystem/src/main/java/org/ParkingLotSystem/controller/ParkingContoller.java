package org.ParkingLotSystem.controller;

import org.ParkingLotSystem.dto.EntryRequestDto;
import org.ParkingLotSystem.dto.EntryResponseDto;
import org.ParkingLotSystem.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/parking")
public class ParkingContoller {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/entry")
    public EntryResponseDto parkingEntry(@RequestBody EntryRequestDto entryRequestDto){
        return parkingService.parkingEntry(entryRequestDto);

    }
}
