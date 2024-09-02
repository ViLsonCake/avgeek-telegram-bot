package project.vilsoncake.telegrambot.service;

import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.entity.enumerated.UnitsSystem;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;

import java.util.List;

public interface UserService {
    boolean isUserExistsByUsername(String username);
    UserEntity getUserByUsername(String username);
    List<UserEntity> findAllUsers();
    boolean addNewUser(UserEntity user);
    UserEntity changeUserState(String username, UserState state);
    UserEntity changeUserBotMode(String username, BotMode botMode);
    UserEntity changeUserAirport(String username, String airportCode);
    UserEntity changeUserEmail(String username, String email);
    UserEntity changeUserEmailCode(String username, int code);
    UserEntity changeUserEmailVerified(String username, boolean verified);
    UserEntity changeUserLanguage(String username, BotLanguage botLanguage);
    UserEntity changeUserUnitsSystem(String username, UnitsSystem unitsSystem);
}
