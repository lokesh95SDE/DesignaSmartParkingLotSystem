package org.ParkingLotSystem.config;

import org.ParkingLotSystem.entity.Floor;
import org.ParkingLotSystem.entity.ParkingLot;
import org.ParkingLotSystem.entity.ParkingSpot;
import org.ParkingLotSystem.enums.SpotStatus;
import org.ParkingLotSystem.enums.SpotType;
import org.ParkingLotSystem.repository.ParkingLotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParkingLotDataInitializer {

    @Bean
    CommandLineRunner seedParkingLot(ParkingLotRepository parkingLotRepository) {
        return args -> {
            if (parkingLotRepository.count() > 0) {
                return;
            }

            ParkingLot parkingLot = new ParkingLot("123 Urban Center", "Smart Parking Hub");

            Floor floorOne = new Floor(1, parkingLot);
            floorOne.addSpot(new ParkingSpot("F1-S1", SpotType.SMALL, SpotStatus.AVAILABLE, floorOne));
            floorOne.addSpot(new ParkingSpot("F1-S2", SpotType.SMALL, SpotStatus.AVAILABLE, floorOne));
            floorOne.addSpot(new ParkingSpot("F1-M1", SpotType.MEDIUM, SpotStatus.AVAILABLE, floorOne));
            floorOne.addSpot(new ParkingSpot("F1-M2", SpotType.MEDIUM, SpotStatus.AVAILABLE, floorOne));
            floorOne.addSpot(new ParkingSpot("F1-L1", SpotType.LARGE, SpotStatus.AVAILABLE, floorOne));

            Floor floorTwo = new Floor(2, parkingLot);
            floorTwo.addSpot(new ParkingSpot("F2-S1", SpotType.SMALL, SpotStatus.AVAILABLE, floorTwo));
            floorTwo.addSpot(new ParkingSpot("F2-M1", SpotType.MEDIUM, SpotStatus.AVAILABLE, floorTwo));
            floorTwo.addSpot(new ParkingSpot("F2-L1", SpotType.LARGE, SpotStatus.AVAILABLE, floorTwo));

            parkingLot.addFloor(floorOne);
            parkingLot.addFloor(floorTwo);

            parkingLotRepository.save(parkingLot);
        };
    }
}
