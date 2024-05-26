package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.repository.FlightRepository;
import project.vilsoncake.telegrambot.service.FlightService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public boolean addFlightToUser(FlightEntity flightEntity) {
        flightRepository.save(flightEntity);
        return true;
    }

    @Override
    public List<FlightEntity> findByUser(UserEntity user) {
        return flightRepository.findByUser(user);
    }

    @Override
    public boolean existsByUserAndFlightId(UserEntity user, String flightId) {
        return flightRepository.existsByUserAndFlightId(user, flightId);
    }
}
