package project.vilsoncake.telegrambot.service;

import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;

import java.util.List;

public interface UserService {
    boolean isUserExistsByUsername(String username);
    UserEntity getUserByUsername(String username);
    List<UserEntity> findAllUsers();
    boolean addNewUser(UserEntity user);
    UserEntity changeUserState(String username, UserState state);
    UserEntity changeUserAirport(String username, String airportCode);
}
