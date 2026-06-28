package org.ParkingLotSystem.entity;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long lotId;
    private String address;
    private String name;
    @OneToMany(mappedBy = "parkingLot")
    private List<Floor> floors;

    public ParkingLot(long lotId, String address, String name, List<Floor> floors) {
        this.lotId = lotId;
        this.address = address;
        this.name = name;
        this.floors = floors;
    }

    public long getLotId() {
        return lotId;
    }

    public void setLotId(long lotId) {
        this.lotId = lotId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }
}
