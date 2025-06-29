package project.vilsoncake.telegrambot.service;

import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;

import java.util.List;

public interface FlightService {
    boolean addFlightToUser(FlightEntity flightEntity);
    List<FlightEntity> findByUserOrderByCreatedAt(UserEntity user);
    List<FlightEntity> findAllByUser(UserEntity user);
    List<FlightEntity> findUniqueFlightsRegistrationsByUser(UserEntity user);
    FlightEntity findByUserAndFlightId(UserEntity user, String flightId);
    FlightEntity findByUserAndRegistration(UserEntity user, String registration);
    boolean changeFlightActive(FlightEntity flight, boolean active);
    boolean changeFlightTookOff(FlightEntity flight, boolean tookOff);
    boolean changeFlightLanding(FlightEntity flight, boolean landing);
    boolean changeFlightOnGround(FlightEntity flight, boolean onGround);
    boolean changeFlightDistance(FlightEntity flight, Integer distance);
    boolean changeFlightFlyingNear(FlightEntity flight, boolean flyingNear);
    boolean changeFlightInUserAirport(FlightEntity flight, boolean inUserAirport);
    boolean changeFlightDepartureAirport(FlightEntity flight, String departureAirport);
    boolean existsByUserAndFlightId(UserEntity user, String flightId);
}
