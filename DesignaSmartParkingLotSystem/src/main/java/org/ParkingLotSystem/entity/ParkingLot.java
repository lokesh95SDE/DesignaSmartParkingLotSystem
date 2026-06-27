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

}
