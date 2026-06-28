package org.ParkingLotSystem.enums;

public enum VehicleType {

    MOTORCYCLE(SpotType.SMALL),
    CAR(SpotType.MEDIUM),
    BUS(SpotType.LARGE);

    private final SpotType spotType;

    VehicleType(SpotType spotType) {
        this.spotType = spotType;
    }

    public SpotType getRequiredSpotType() {
        return spotType;
    }
}