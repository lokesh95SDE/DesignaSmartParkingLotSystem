package org.ParkingLotSystem.repository;

import org.ParkingLotSystem.entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    Optional<ParkingTicket> findByTicketIdAndExitTimeIsNull(Long ticketId);

    Optional<ParkingTicket> findByVehicle_RegistrationNumberAndExitTimeIsNull(String registrationNumber);

    List<ParkingTicket> findByExitTimeIsNull();
}
