package project.vilsoncake.botadminpanel.repository;

import project.vilsoncake.botadminpanel.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends ReadOnlyRepository<UserEntity, UUID> {
}
