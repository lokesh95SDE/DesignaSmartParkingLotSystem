package org.ParkingLotSystem.service;

import org.ParkingLotSystem.dto.AvailabilityResponseDto;
import org.ParkingLotSystem.dto.EntryRequestDto;
import org.ParkingLotSystem.dto.EntryResponseDto;
import org.ParkingLotSystem.dto.ExitResponseDto;
import org.ParkingLotSystem.dto.ParkedVehicleResponseDto;
import org.ParkingLotSystem.entity.ParkingSpot;
import org.ParkingLotSystem.entity.ParkingTicket;
import org.ParkingLotSystem.entity.Vehicle;
import org.ParkingLotSystem.enums.SpotStatus;
import org.ParkingLotSystem.exception.TicketNotFoundException;
import org.ParkingLotSystem.exception.VehicleAlreadyParkedException;
import org.ParkingLotSystem.repository.ParkingSlotRepository;
import org.ParkingLotSystem.repository.ParkingTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {

    private static final BigDecimal MOTORCYCLE_RATE_PER_HOUR = BigDecimal.valueOf(10);
    private static final BigDecimal CAR_RATE_PER_HOUR = BigDecimal.valueOf(20);
    private static final BigDecimal BUS_RATE_PER_HOUR = BigDecimal.valueOf(50);

    private final VehicleService vehicleService;
    private final SpotAllocationService spotAllocationService;
    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingTicketRepository parkingTicketRepository;
    private final Clock clock;

    public ParkingService(
            VehicleService vehicleService,
            SpotAllocationService spotAllocationService,
            ParkingSlotRepository parkingSlotRepository,
            ParkingTicketRepository parkingTicketRepository,
            Clock clock
    ) {
        this.vehicleService = vehicleService;
        this.spotAllocationService = spotAllocationService;
        this.parkingSlotRepository = parkingSlotRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.clock = clock;
    }

    @Transactional
    public EntryResponseDto parkingEntry(EntryRequestDto entryRequestDto) {
        String normalizedRegistration = normalizeRegistration(entryRequestDto.vehicleRegistrationNumber());

        parkingTicketRepository.findByVehicle_RegistrationNumberAndExitTimeIsNull(normalizedRegistration)
                .ifPresent(ticket -> {
                    throw new VehicleAlreadyParkedException(
                            "Vehicle is already parked with active ticket id " + ticket.getTicketId()
                    );
                });

        Vehicle vehicle = vehicleService.findByRegistrationNumber(normalizedRegistration)
                .map(existingVehicle -> updateVehicleType(existingVehicle, entryRequestDto))
                .orElseGet(() -> vehicleService.saveVehicle(
                        new Vehicle(normalizedRegistration, entryRequestDto.vehicleType())
                ));

        ParkingSpot parkingSpot = spotAllocationService.allocateSpot(entryRequestDto.vehicleType().getRequiredSpotType());
        parkingSpot.setCurrentVehicle(vehicle);

        LocalDateTime entryTime = LocalDateTime.now(clock);
        ParkingTicket parkingTicket = new ParkingTicket(entryTime, vehicle, parkingSpot);
        ParkingTicket savedTicket = parkingTicketRepository.save(parkingTicket);

        return new EntryResponseDto(
                savedTicket.getTicketId(),
                savedTicket.getEntryTime(),
                parkingSpot.getSpotNumber(),
                parkingSpot.getFloor().getFloorNumber(),
                "Vehicle checked in successfully"
        );
    }

    @Transactional
    public ExitResponseDto parkingExit(Long ticketId) {
        ParkingTicket parkingTicket = parkingTicketRepository.findByTicketIdAndExitTimeIsNull(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Active ticket not found for id " + ticketId));

        LocalDateTime exitTime = LocalDateTime.now(clock);
        parkingTicket.setExitTime(exitTime);

        long parkedMinutes = Math.max(1, Duration.between(parkingTicket.getEntryTime(), exitTime).toMinutes());
        BigDecimal amount = calculateFee(parkingTicket, parkedMinutes);
        parkingTicket.setAmount(amount);

        ParkingSpot parkingSpot = parkingTicket.getParkingSpot();
        parkingSpot.setSpotStatus(SpotStatus.AVAILABLE);
        parkingSpot.setCurrentVehicle(null);
        parkingSlotRepository.save(parkingSpot);
        parkingTicketRepository.save(parkingTicket);

        return new ExitResponseDto(
                parkingTicket.getTicketId(),
                parkingTicket.getEntryTime(),
                parkingTicket.getExitTime(),
                parkedMinutes,
                parkingTicket.getAmount(),
                "Vehicle checked out successfully"
        );
    }

    @Transactional(readOnly = true)
    public List<AvailabilityResponseDto> getAvailableSpots() {
        return parkingSlotRepository.findBySpotStatusOrderByFloor_FloorNumberAscSpotNumberAsc(SpotStatus.AVAILABLE)
                .stream()
                .map(spot -> new AvailabilityResponseDto(
                        spot.getSpotId(),
                        spot.getSpotNumber(),
                        spot.getSpotType(),
                        spot.getSpotStatus().name(),
                        spot.getFloor().getFloorNumber()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ParkedVehicleResponseDto> getParkedVehicles() {
        return parkingTicketRepository.findByExitTimeIsNull()
                .stream()
                .map(ticket -> new ParkedVehicleResponseDto(
                        ticket.getTicketId(),
                        ticket.getVehicle().getRegistrationNumber(),
                        ticket.getVehicle().getVehicleType(),
                        ticket.getParkingSpot().getSpotNumber(),
                        ticket.getParkingSpot().getFloor().getFloorNumber()
                ))
                .toList();
    }

    private Vehicle updateVehicleType(Vehicle existingVehicle, EntryRequestDto entryRequestDto) {
        existingVehicle.setVehicleType(entryRequestDto.vehicleType());
        return vehicleService.saveVehicle(existingVehicle);
    }

    private String normalizeRegistration(String registrationNumber) {
        return registrationNumber.trim().toUpperCase();
    }

    private BigDecimal calculateFee(ParkingTicket parkingTicket, long parkedMinutes) {
        long billedHours = (long) Math.ceil(parkedMinutes / 60.0);
        BigDecimal hourlyRate = switch (parkingTicket.getVehicle().getVehicleType()) {
            case MOTORCYCLE -> MOTORCYCLE_RATE_PER_HOUR;
            case CAR -> CAR_RATE_PER_HOUR;
            case BUS -> BUS_RATE_PER_HOUR;
        };

        return hourlyRate.multiply(BigDecimal.valueOf(billedHours)).setScale(2, RoundingMode.HALF_UP);
    }
}
