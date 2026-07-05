package org.ParkingLotSystem.service;

import org.ParkingLotSystem.entity.ParkingSpot;
import org.ParkingLotSystem.enums.SpotStatus;
import org.ParkingLotSystem.enums.SpotType;
import org.ParkingLotSystem.exception.NoSpotAvailableException;
import org.ParkingLotSystem.repository.ParkingSlotRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpotAllocationService {

    private final ParkingSlotRepository parkingSlotRepository;

    public SpotAllocationService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @Transactional
    public ParkingSpot allocateSpot(SpotType requiredSpotType) {
        List<ParkingSpot> availableSpots = parkingSlotRepository.findFirstAvailableSpotForUpdate(
                requiredSpotType,
                SpotStatus.AVAILABLE,
                PageRequest.of(0, 1)
        );

        if (availableSpots.isEmpty()) {
            throw new NoSpotAvailableException("No available spot found for vehicle type requiring " + requiredSpotType);
        }

        ParkingSpot allocatedSpot = availableSpots.get(0);
        allocatedSpot.setSpotStatus(SpotStatus.OCCUPIED);
        return allocatedSpot;
    }
}
