package project.vilsoncake.botadminpanel.repository;

import project.vilsoncake.botadminpanel.entity.jpa.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends ReadOnlyRepository<UserEntity, UUID> {
    List<UserEntity> findAllByOrderByCreatedAtDesc();
}
