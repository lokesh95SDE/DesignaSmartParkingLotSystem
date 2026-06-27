package org.ParkingLotSystem.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long floorId;

    private Integer floorNumber;

    @ManyToOne
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "floor")
    private List<ParkingSpot> parkingSpots;
}
