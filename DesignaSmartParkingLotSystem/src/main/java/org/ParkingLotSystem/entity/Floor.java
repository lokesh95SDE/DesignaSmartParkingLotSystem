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


    public Floor(Long floorId, Integer floorNumber, ParkingLot parkingLot, List<ParkingSpot> parkingSpots) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.parkingLot = parkingLot;
        this.parkingSpots = parkingSpots;
    }


    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }
}
