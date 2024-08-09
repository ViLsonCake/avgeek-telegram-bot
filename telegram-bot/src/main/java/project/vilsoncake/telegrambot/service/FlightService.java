package project.vilsoncake.telegrambot.service;

import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;

import java.util.List;

public interface FlightService {
    boolean addFlightToUser(FlightEntity flightEntity);
    List<FlightEntity> findByUserOrderByCreatedAt(UserEntity user);
    FlightEntity findByUserAndFlightId(UserEntity user, String flightId);
    boolean changeFlightActive(FlightEntity flight, boolean active);
    boolean changeFlightTookOff(FlightEntity flight, boolean tookOff);
    boolean changeFlightLanding(FlightEntity flight, boolean landing);
    boolean changeFlightOnGround(FlightEntity flight, boolean onGround);
    boolean changeFlightDistance(FlightEntity flight, Integer distance);
    boolean existsByUserAndFlightId(UserEntity user, String flightId);
}
