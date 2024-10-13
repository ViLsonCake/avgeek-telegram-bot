package project.vilsoncake.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.telegrambot.entity.AircraftEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.AircraftFamily;

import java.util.List;
import java.util.UUID;

public interface AircraftRepository extends CrudRepository<AircraftEntity, UUID> {
    List<AircraftEntity> findAllByUser(UserEntity user);
    boolean existsByFamilyAndUser(AircraftFamily family, UserEntity user);
}
