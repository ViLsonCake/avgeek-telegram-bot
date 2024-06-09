package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import project.vilsoncake.telegrambot.dto.AirportNameDto;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;
import project.vilsoncake.telegrambot.service.BotService;
import project.vilsoncake.telegrambot.service.MailService;
import project.vilsoncake.telegrambot.service.UserService;
import project.vilsoncake.telegrambot.utils.VerifyUtils;

import java.util.List;

import static project.vilsoncake.telegrambot.constant.BotMessageConst.*;
import static project.vilsoncake.telegrambot.constant.CommandConst.MODES;
import static project.vilsoncake.telegrambot.constant.MailMessageConst.CODE_MESSAGE_SUBJECT;
import static project.vilsoncake.telegrambot.constant.MailMessageConst.CODE_MESSAGE_TEXT;
import static project.vilsoncake.telegrambot.entity.enumerated.UserState.*;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final UserService userService;
    private final MailService mailService;
    private final VerifyUtils verifyUtils;
    private final WebClient webClient;

    @Override
    public SendMessage pingCommand(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("pong");

        return message;
    }

    @Override
    public SendMessage startBotCommand(String username, Long chatId) {

        UserEntity user = new UserEntity(
                username,
                chatId,
                CHOOSING_AIRPORT
        );

        if (!userService.addNewUser(user)) {
            userService.changeUserState(username, CHOOSING_AIRPORT);
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format(START_TEXT, username));

        return message;
    }

    @Override
    public SendMessage changeUserAirportCommand(String username, Long chatId) {
        userService.changeUserState(username, CHOOSING_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(CHANGE_AIRPORT_TEXT);

        return message;
    }

    @Override
    public SendMessage getUserAirportCommand(String username, Long chatId) {
        if (userService.getUserByUsername(username).getState().equals(CHOOSING_AIRPORT)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(USER_CANNOT_CHOOSE_AIRPORT_TEXT);

            return message;
        }

        String airportCode = userService.getUserByUsername(username).getAirport();

        AirportNameDto airportNameDto = webClient.get()
                .uri("/airport/name/" + airportCode)
                .retrieve()
                .bodyToMono(AirportNameDto.class)
                .block();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format(CURRENT_AIRPORT_TEXT, airportNameDto.getName(), airportCode));

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
            message.setText(String.format(INVALID_AIRPORT_CODE_TEXT, code));

            return message;
        }

        userService.changeUserAirport(username, code);
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format(CHOOSE_AIRPORT_TEXT, airportNameDto.getName()));

        return message;
    }

    @Override
    public SendMessage setEmailCommand(String username, Long chatId) {
        userService.changeUserState(username, WAIT_FOR_EMAIL);
        userService.changeUserEmailVerified(username, false);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(WAIT_FOR_EMAIL_TEXT);

        return message;
    }

    @Override
    public SendMessage myEmailCommand(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        if (user.getEmail() == null) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(USER_NOT_ADDED_EMAIL_TEXT);
            return message;
        }
        if (!user.isEmailVerified()) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(String.format(EMAIL_NOT_VERIFY_EMAIL, user.getEmail()));
            return message;
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format(MY_EMAIL_TEXT, user.getEmail()));
        return message;
    }

    @Override
    public SendMessage setEmail(String username, String email, Long chatId) {
        if (!verifyUtils.isEmailValid(email)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(INVALID_EMAIL_TEXT);

            return message;
        }

        userService.changeUserEmail(username, email);
        userService.changeUserState(username, WAIT_FOR_EMAIL_CODE);

        int code = verifyUtils.generateCode();

        mailService.sendMessage(email, CODE_MESSAGE_SUBJECT, String.format(CODE_MESSAGE_TEXT, code));

        userService.changeUserEmailCode(username, code);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(WAIT_FOR_CODE_TEXT);

        return message;
    }

    @Override
    public SendMessage removeEmail(String username, Long chatId) {
        userService.changeUserEmail(username, null);
        userService.changeUserEmailVerified(username, false);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(REMOVE_EMAIL_TEXT);

        return message;
    }

    @Override
    public SendMessage cancelEmail(String username, Long chatId) {
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(CANCEL_ADDING_EMAIL_TEXT);

        return message;
    }

    @Override
    public SendMessage verifyEmail(String username, String code, Long chatId) {
        int userCode = userService.getUserByUsername(username).getEmailCode();

        if (!String.valueOf(userCode).equals(code)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(INCORRECT_EMAIL_VERIFY_CODE);
            return message;
        }

        userService.changeUserState(username, CHOSEN_AIRPORT);
        userService.changeUserEmailVerified(username, true);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(VERIFY_EMAIL_TEXT);

        return message;
    }

    @Override
    public SendMessage changeBotModeCommand(String username, Long chatId) {
        userService.changeUserState(username, CHOOSING_MODE);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(MODES);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(List.of(keyboardRow));
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(keyboardMarkup);
        message.setText(CHOOSING_MODE_TEXT);

        return message;
    }

    @Override
    public SendMessage changeBotMode(String username, BotMode botMode, Long chatId) {
        userService.changeUserBotMode(username, botMode);
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(new ReplyKeyboardRemove(true));

        switch (botMode) {
            case ALL -> message.setText(CHOSEN_MODE_ALL_TEXT);
            case ONLY_AN_124_FLIGHTS -> message.setText(CHOSEN_MODE_ONLY_AN_124_TEXT);
            case ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS -> message.setText(CHOSEN_MODE_ONLY_WIDE_BODY_TEXT);
        }

        return message;
    }

    @Override
    public SendMessage getBotMode(String username, Long chatId) {
        BotMode botMode = userService.getUserByUsername(username).getBotMode();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        switch (botMode) {
            case ALL -> message.setText(CHOSEN_MODE_ALL_TEXT);
            case ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS -> message.setText(CHOSEN_MODE_ONLY_WIDE_BODY_TEXT);
            case ONLY_AN_124_FLIGHTS -> message.setText(CHOSEN_MODE_ONLY_AN_124_TEXT);
        }

        return message;
    }

    @Override
    public SendMessage incorrectModeMessage(String username, String mode, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format(INCORRECT_MODE_TEXT, mode));
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
