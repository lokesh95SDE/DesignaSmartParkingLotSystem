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

    @OneToOne
    private Vehicle currentVehicle;

    @Version
    private Long version;
    @ManyToOne
    private Floor floor;


    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(Vehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public SpotStatus getSpotStatus() {
        return spotStatus;
    }

    public void setSpotStatus(SpotStatus spotStatus) {
        this.spotStatus = spotStatus;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public ParkingSpot(Long spotId, String spotNumber, SpotType spotType, SpotStatus spotStatus, Vehicle currentVehicle, Long version, Floor floor) {
        this.spotId = spotId;
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.spotStatus = spotStatus;
        this.currentVehicle = currentVehicle;
        this.version = version;
        this.floor = floor;
    }

}
