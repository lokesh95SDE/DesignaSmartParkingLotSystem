package org.ParkingLotSystem.service;

import org.ParkingLotSystem.entity.Vehicle;
import org.ParkingLotSystem.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> findByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
    }

    @Transactional(readOnly = true)
    public boolean existsByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.existsByRegistrationNumber(registrationNumber);
    }
}
