package project.vilsoncake.telegrambot.service;

import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;

import java.util.List;

public interface FlightService {
    boolean addFlightToUser(FlightEntity flightEntity);
    List<FlightEntity> findByUser(UserEntity user);
    FlightEntity findByUserAndFlightId(UserEntity user, String flightId);
    boolean changeFlightActive(FlightEntity flight, boolean active);
    boolean changeFlightDistance(FlightEntity flight, Integer distance);
    boolean existsByUserAndFlightId(UserEntity user, String flightId);
}
