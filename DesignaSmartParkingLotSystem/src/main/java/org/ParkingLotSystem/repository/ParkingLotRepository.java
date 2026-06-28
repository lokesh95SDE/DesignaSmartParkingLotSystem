package org.ParkingLotSystem.repository;

import org.ParkingLotSystem.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {
}
