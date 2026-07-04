package org.ParkingLotSystem.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "floors")
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long floorId;

    @Column(nullable = false)
    private Integer floorNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSpot> parkingSpots = new ArrayList<>();

    protected Floor() {
    }

    public Floor(Integer floorNumber, ParkingLot parkingLot) {
        this.floorNumber = floorNumber;
        this.parkingLot = parkingLot;
    }

    public Long getFloorId() {
        return floorId;
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

    public void addSpot(ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
        parkingSpot.setFloor(this);
    }
}
