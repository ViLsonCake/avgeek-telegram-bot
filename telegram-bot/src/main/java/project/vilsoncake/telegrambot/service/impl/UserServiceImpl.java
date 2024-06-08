package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;
import project.vilsoncake.telegrambot.exception.UserNotFoundException;
import project.vilsoncake.telegrambot.repository.UserRepository;
import project.vilsoncake.telegrambot.service.UserService;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean isUserExistsByUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UserNotFoundException(String.format("User \"%s\" not found", username)));
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public boolean addNewUser(UserEntity user) {
        if (isUserExistsByUsername(user.getUsername())) {
            return false;
        }

        userRepository.save(user);
        return true;
    }

    @Override
    public UserEntity changeUserState(String username, UserState state) {
        UserEntity user = getUserByUsername(username);
        user.setState(state);
        return userRepository.save(user);
    }

    @Override
    public UserEntity changeUserBotMode(String username, BotMode botMode) {
        UserEntity user = getUserByUsername(username);
        user.setBotMode(botMode);
        return userRepository.save(user);
    }

    @Override
    public UserEntity changeUserAirport(String username, String airportCode) {
        UserEntity user = getUserByUsername(username);
        user.setAirport(airportCode);
        return userRepository.save(user);
    }
}
