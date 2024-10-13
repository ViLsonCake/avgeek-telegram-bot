package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.telegrambot.entity.AircraftEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.repository.AircraftRepository;
import project.vilsoncake.telegrambot.service.AircraftService;

import java.util.List;

import static project.vilsoncake.telegrambot.constant.NumberConst.MAX_SELECTED_AIRCRAFT_COUNT;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;

    @Override
    public boolean addAircraftToUser(AircraftEntity aircraftEntity) {
        List<AircraftEntity> aircraftEntities = aircraftRepository.findAllByUser(aircraftEntity.getUser());

        if (aircraftEntities.size() <= MAX_SELECTED_AIRCRAFT_COUNT && !aircraftRepository.existsByFamilyAndUser(aircraftEntity.getFamily(), aircraftEntity.getUser())) {
            aircraftRepository.save(aircraftEntity);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteAircraftFromUser(UserEntity userEntity) {
        aircraftRepository.deleteAll(aircraftRepository.findAllByUser(userEntity));

        return true;
    }

    @Override
    public List<AircraftEntity> findAllAircraftByUser(UserEntity userEntity) {
        return aircraftRepository.findAllByUser(userEntity);
    }
}
