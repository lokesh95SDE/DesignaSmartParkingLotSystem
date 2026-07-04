package org.ParkingLotSystem.service;

import org.ParkingLotSystem.entity.ParkingSpot;
import org.ParkingLotSystem.enums.SpotStatus;
import org.ParkingLotSystem.enums.SpotType;
import org.ParkingLotSystem.exception.NoSpotAvailableException;
import org.ParkingLotSystem.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotAllocationService {

    private final ParkingSlotRepository parkingSlotRepository;

    public SpotAllocationService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public ParkingSpot allocateSpot(SpotType requiredSpotType) {
        List<ParkingSpot> availableSpots = parkingSlotRepository.findAvailableSpotsForUpdate(
                requiredSpotType,
                SpotStatus.AVAILABLE
        );

        if (availableSpots.isEmpty()) {
            throw new NoSpotAvailableException("No available spot found for vehicle type requiring " + requiredSpotType);
        }

        return availableSpots.get(0);
    }
}
