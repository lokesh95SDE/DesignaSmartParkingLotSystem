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

    public ParkingTicket(Long ticketId, LocalDateTime entryTime, LocalDateTime exitTime, Double amount, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.ticketId = ticketId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amount = amount;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
}
