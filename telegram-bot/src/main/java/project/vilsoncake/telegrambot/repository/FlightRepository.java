package project.vilsoncake.telegrambot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends CrudRepository<FlightEntity, UUID> {
    @Query(value = "SELECT DISTINCT ON (f.registration) * from flight f WHERE f.active = true AND f.registration IS NOT NULL", nativeQuery = true)
    List<FlightEntity> findUniqueFlightsRegistrations();
    List<FlightEntity> findByUserOrderByCreatedAt(UserEntity user);
    List<FlightEntity> findAllByUser(UserEntity user);
    Optional<FlightEntity> findByUserAndFlightId(UserEntity user, String flightId);
    Optional<FlightEntity> findTopByUserAndRegistrationOrderByCreatedAtDesc(UserEntity user, String registration);
    boolean existsByUserAndFlightId(UserEntity user, String flightId);
}
