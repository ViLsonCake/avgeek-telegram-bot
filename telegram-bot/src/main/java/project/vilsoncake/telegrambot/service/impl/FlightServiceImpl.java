package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.exception.FlightNotFoundException;
import project.vilsoncake.telegrambot.repository.FlightRepository;
import project.vilsoncake.telegrambot.service.FlightService;

import java.util.List;

import static project.vilsoncake.telegrambot.constant.NumberConst.MAX_SAVED_FLIGHTS_COUNT;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public boolean addFlightToUser(FlightEntity flightEntity) {
        List<FlightEntity> flights = findByUserOrderByCreatedAt(flightEntity.getUser());

        if (flights.size() > MAX_SAVED_FLIGHTS_COUNT) {
            List<FlightEntity> flightsToDelete = flights.subList(0, flights.size() - MAX_SAVED_FLIGHTS_COUNT + 1);
            flightRepository.deleteAll(flightsToDelete);
        }

        flightRepository.save(flightEntity);
        return true;
    }

    @Override
    public List<FlightEntity> findByUserOrderByCreatedAt(UserEntity user) {
        return flightRepository.findByUserOrderByCreatedAt(user);
    }

    @Override
    public List<FlightEntity> findAllByUser(UserEntity user) {
        return flightRepository.findAllByUser(user);
    }

    @Override
    public FlightEntity findByUserAndFlightId(UserEntity user, String flightId) {
        return flightRepository.findByUserAndFlightId(user, flightId).orElseThrow(() ->
                new FlightNotFoundException(String.format("Flight with id %s not found", flightId)));
    }

    @Override
    public boolean changeFlightActive(FlightEntity flight, boolean active) {
        flight.setActive(active);
        flightRepository.save(flight);

        return true;
    }

    @Override
    public boolean changeFlightTookOff(FlightEntity flight, boolean tookOff) {
        flight.setTookOff(tookOff);
        flightRepository.save(flight);

        return true;
    }

    @Override
    public boolean changeFlightLanding(FlightEntity flight, boolean landing) {
        flight.setLanding(landing);
        flightRepository.save(flight);

        return true;
    }

    @Override
    public boolean changeFlightOnGround(FlightEntity flight, boolean onGround) {
        flight.setOnGround(onGround);
        flightRepository.save(flight);

        return true;
    }

    @Override
    public boolean changeFlightDistance(FlightEntity flight, Integer distance) {
        flight.setDistance(distance);
        flightRepository.save(flight);

        return true;
    }

    @Override
    public boolean changeFlightDepartureAirport(FlightEntity flight, String departureAirport) {
        flight.setDepartureAirport(departureAirport);
        flightRepository.save(flight);

        return true;
    }

    @Override
    public boolean existsByUserAndFlightId(UserEntity user, String flightId) {
        return flightRepository.existsByUserAndFlightId(user, flightId);
    }
}
