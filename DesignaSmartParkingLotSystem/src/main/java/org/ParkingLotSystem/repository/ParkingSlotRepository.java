package org.ParkingLotSystem.repository;

import org.ParkingLotSystem.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSpot,Long> {
}
