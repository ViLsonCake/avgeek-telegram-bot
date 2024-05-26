package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.telegrambot.constant.MessageConst;
import project.vilsoncake.telegrambot.dto.AirportNameDto;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;
import project.vilsoncake.telegrambot.service.BotService;
import project.vilsoncake.telegrambot.service.UserService;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final UserService userService;
    private final WebClient webClient;

    @Override
    public SendMessage startBotCommand(String username, Long chatId) {

        UserEntity user = new UserEntity(
                username,
                chatId,
                UserState.CHOOSING_AIRPORT
        );

        if (!userService.addNewUser(user)) {
            userService.changeUserState(username, UserState.CHOOSING_AIRPORT);
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format(MessageConst.START_TEXT, username));

        return message;
    }

    @Override
    public SendMessage changeUserAirportCommand(String username, Long chatId) {
        userService.changeUserState(username, UserState.CHOOSING_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(MessageConst.CHANGE_AIRPORT_TEXT);

        return message;
    }

    @Override
    public SendMessage changeUserAirport(String username, String code, Long chatId) {
        AirportNameDto airportNameDto;
        try {
            airportNameDto = webClient.get()
                    .uri("/airport/name/" + code)
                    .retrieve()
                    .bodyToMono(AirportNameDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(String.format(MessageConst.INVALID_AIRPORT_CODE_TEXT, code));

            return message;
        }

        userService.changeUserAirport(username, code);
        userService.changeUserState(username, UserState.CHOSEN_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format(MessageConst.CHOOSE_AIRPORT_TEXT, airportNameDto.getName()));

        return message;
    }

    @Override
    public UserState getUserState(String username) {
        return userService.getUserByUsername(username).getState();
    }

    @Override
    public boolean isUserRegistered(String username) {
        return userService.isUserExistsByUsername(username);
    }
}
