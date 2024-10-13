package project.vilsoncake.telegrambot.service;

import project.vilsoncake.telegrambot.entity.AircraftEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;

import java.util.List;

public interface AircraftService {
    boolean addAircraftToUser(AircraftEntity aircraftEntity);
    boolean deleteAircraftFromUser(UserEntity userEntity);
    List<AircraftEntity> findAllAircraftByUser(UserEntity userEntity);
}
