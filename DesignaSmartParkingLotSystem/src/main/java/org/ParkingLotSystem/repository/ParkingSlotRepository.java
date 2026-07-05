package org.ParkingLotSystem.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.ParkingLotSystem.entity.ParkingSpot;
import org.ParkingLotSystem.enums.SpotStatus;
import org.ParkingLotSystem.enums.SpotType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSpot, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    @Query("""
            select ps
            from ParkingSpot ps
            join fetch ps.floor f
            where ps.spotType = :spotType and ps.spotStatus = :spotStatus
            order by f.floorNumber asc, ps.spotNumber asc
            """)
    List<ParkingSpot> findFirstAvailableSpotForUpdate(
            @Param("spotType") SpotType spotType,
            @Param("spotStatus") SpotStatus spotStatus,
            Pageable pageable
    );

    List<ParkingSpot> findBySpotStatusOrderByFloor_FloorNumberAscSpotNumberAsc(SpotStatus spotStatus);

}
