package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.telegrambot.constant.MessageConst;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;
import project.vilsoncake.telegrambot.service.BotService;
import project.vilsoncake.telegrambot.service.UserService;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final UserService userService;

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
    public SendMessage changeUserAirportCommand(String username, String code, Long chatId) {

        // TODO add code validation

        userService.changeUserState(username, UserState.CHOOSING_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(MessageConst.CHANGE_AIRPORT_TEXT);

        return message;
    }

    @Override
    public SendMessage changeUserAirport(String username, String code) {

        // TODO add code validation

        userService.changeUserAirport(username, code);
        UserEntity user = userService.changeUserState(username, UserState.CHOSEN_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(user.getChatId());
        message.setText(String.format(MessageConst.CHOOSE_AIRPORT_TEXT, code));

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
