package org.ParkingLotSystem.entity;

import jakarta.persistence.*;
import org.ParkingLotSystem.enums.SpotStatus;
import org.ParkingLotSystem.enums.SpotType;

@Entity
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long spotId;

    private String spotNumber;
    @Enumerated(EnumType.STRING)
    private SpotType spotType;
    @Enumerated(EnumType.STRING)
    private SpotStatus spotStatus;
    @Version
    private Long version;
    @ManyToOne
    private Floor floor;
    
}
