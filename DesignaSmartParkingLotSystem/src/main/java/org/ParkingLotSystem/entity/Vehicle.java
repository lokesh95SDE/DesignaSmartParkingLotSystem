package org.ParkingLotSystem.entity;

import jakarta.persistence.*;
import org.ParkingLotSystem.enums.VehicleType;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vehicleId;
    private String registrationNumber;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
