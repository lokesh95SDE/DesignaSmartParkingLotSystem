package org.ParkingLotSystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ParkingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketId;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Double amount;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private ParkingSpot parkingSpot;
}
