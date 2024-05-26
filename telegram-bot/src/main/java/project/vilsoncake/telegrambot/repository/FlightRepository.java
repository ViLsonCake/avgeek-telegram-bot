package project.vilsoncake.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface FlightRepository extends CrudRepository<FlightEntity, UUID> {
    List<FlightEntity> findByUser(UserEntity user);
    boolean existsByUserAndFlightId(UserEntity user, String flightId);
}
