package project.vilsoncake.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.telegrambot.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByChatId(Long chatId);
    boolean existsByUsernameIgnoreCase(String username);
}
