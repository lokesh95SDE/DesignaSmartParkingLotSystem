package org.ParkingLotSystem.repository;

import jakarta.persistence.LockModeType;
import org.ParkingLotSystem.entity.ParkingSpot;
import org.ParkingLotSystem.enums.SpotStatus;
import org.ParkingLotSystem.enums.SpotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSpot, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select ps
            from ParkingSpot ps
            join fetch ps.floor f
            where ps.spotType = :spotType and ps.spotStatus = :spotStatus
            order by f.floorNumber asc, ps.spotNumber asc
            """)
    List<ParkingSpot> findAvailableSpotsForUpdate(SpotType spotType, SpotStatus spotStatus);

    List<ParkingSpot> findBySpotStatusOrderByFloor_FloorNumberAscSpotNumberAsc(SpotStatus spotStatus);

}
