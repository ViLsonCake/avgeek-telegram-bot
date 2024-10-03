package project.vilsoncake.telegrambot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.telegrambot.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    @Query(value = "SELECT DISTINCT u.airport FROM user_ u WHERE u.airport IS NOT NULL AND (u.bot_mode = 'ALL' OR u.bot_mode = 'ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS')", nativeQuery = true)
    List<String> findUniqueAirports();
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByChatId(Long chatId);
    boolean existsByUsernameIgnoreCase(String username);
}
