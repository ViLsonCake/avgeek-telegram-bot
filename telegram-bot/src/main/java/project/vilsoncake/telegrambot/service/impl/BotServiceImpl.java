package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import project.vilsoncake.telegrambot.dto.AirportCodesDto;
import project.vilsoncake.telegrambot.dto.GeonameDto;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;
import project.vilsoncake.telegrambot.exception.AirportNotFoundException;
import project.vilsoncake.telegrambot.service.BotService;
import project.vilsoncake.telegrambot.service.GeonameService;
import project.vilsoncake.telegrambot.service.MailService;
import project.vilsoncake.telegrambot.service.UserService;
import project.vilsoncake.telegrambot.utils.AirportsUtils;
import project.vilsoncake.telegrambot.utils.BotMessageUtils;
import project.vilsoncake.telegrambot.utils.MailMessageUtils;
import project.vilsoncake.telegrambot.utils.VerifyUtils;

import java.util.List;

import static project.vilsoncake.telegrambot.constant.CommandConst.*;
import static project.vilsoncake.telegrambot.entity.enumerated.BotMessageTemplate.*;
import static project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate.CODE_MESSAGE_SUBJECT;
import static project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate.CODE_MESSAGE_TEXT;
import static project.vilsoncake.telegrambot.entity.enumerated.UserState.*;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final UserService userService;
    private final MailService mailService;
    private final GeonameService geonameService;
    private final VerifyUtils verifyUtils;
    private final AirportsUtils airportsUtils;
    private final BotMessageUtils botMessageUtils;
    private final MailMessageUtils mailMessageUtils;

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
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(START_TEXT, user.getBotLanguage()), username));

        return message;
    }

    @Override
    public SendMessage changeUserAirportCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOOSING_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(CHANGE_AIRPORT_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage getUserAirportCommand(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        if (user.getState().equals(CHOOSING_AIRPORT)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setParseMode(MARKDOWN_PARSE_MODE);
            message.setText(botMessageUtils.getMessageByLanguage(USER_CANNOT_CHOOSE_AIRPORT_TEXT, user.getBotLanguage()));

            return message;
        }

        String airportCode = userService.getUserByUsername(username).getAirport();

        AirportCodesDto airportCodesDto = airportsUtils.validateAirportCode(airportCode);

        GeonameDto geonameDto = geonameService.getObject(airportCodesDto.getIcao(), user.getBotLanguage().name());

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(CURRENT_AIRPORT_TEXT, user.getBotLanguage()), geonameDto.getName(), airportCode));

        return message;
    }

    @Override
    public SendMessage changeUserAirport(String username, String code, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        AirportCodesDto airportCodesDto;
        try {
            airportCodesDto = airportsUtils.validateAirportCode(code);
        } catch (AirportNotFoundException e) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setParseMode(MARKDOWN_PARSE_MODE);
            message.setText(String.format(botMessageUtils.getMessageByLanguage(INVALID_AIRPORT_CODE_TEXT, user.getBotLanguage()), code));

            return message;
        }

        GeonameDto geonameDto = geonameService.getObject(airportCodesDto.getIcao(), user.getBotLanguage().name());

        userService.changeUserAirport(username, airportCodesDto.getIata());
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(CHOOSE_AIRPORT_TEXT, user.getBotLanguage()), geonameDto.getName()));

        return message;
    }

    @Override
    public SendMessage setEmailCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, WAIT_FOR_EMAIL);
        userService.changeUserEmailVerified(username, false);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(WAIT_FOR_EMAIL_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage myEmailCommand(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        if (user.getEmail() == null) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setParseMode(MARKDOWN_PARSE_MODE);
            message.setText(botMessageUtils.getMessageByLanguage(USER_NOT_ADDED_EMAIL_TEXT, user.getBotLanguage()));
            return message;
        }
        if (!user.isEmailVerified()) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setParseMode(MARKDOWN_PARSE_MODE);
            message.setText(String.format(botMessageUtils.getMessageByLanguage(EMAIL_NOT_VERIFY_EMAIL, user.getBotLanguage()), user.getEmail()));
            return message;
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(MY_EMAIL_TEXT, user.getBotLanguage()), user.getEmail()));
        return message;
    }

    @Override
    public SendMessage setEmail(String username, String email, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        if (!verifyUtils.isEmailValid(email)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setParseMode(MARKDOWN_PARSE_MODE);
            message.setText(botMessageUtils.getMessageByLanguage(INVALID_EMAIL_TEXT, user.getBotLanguage()));

            return message;
        }

        userService.changeUserEmail(username, email);
        userService.changeUserState(username, WAIT_FOR_EMAIL_CODE);

        int code = verifyUtils.generateCode();

        mailService.sendMessage(email, mailMessageUtils.getMessageByLanguage(CODE_MESSAGE_SUBJECT, user.getBotLanguage()), String.format(mailMessageUtils.getMessageByLanguage(CODE_MESSAGE_TEXT, user.getBotLanguage()), code));

        userService.changeUserEmailCode(username, code);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(WAIT_FOR_CODE_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage removeEmail(String username, Long chatId) {
        UserEntity user = userService.changeUserEmail(username, null);
        userService.changeUserEmailVerified(username, false);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(REMOVE_EMAIL_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage cancelEmail(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(CANCEL_ADDING_EMAIL_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage verifyEmail(String username, String code, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        if (!String.valueOf(user.getEmailCode()).equals(code)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setParseMode(MARKDOWN_PARSE_MODE);
            message.setText(botMessageUtils.getMessageByLanguage(INCORRECT_EMAIL_VERIFY_CODE, user.getBotLanguage()));
            return message;
        }

        userService.changeUserState(username, CHOSEN_AIRPORT);
        userService.changeUserEmailVerified(username, true);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(VERIFY_EMAIL_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage changeBotModeCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOOSING_MODE);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(MODES);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(List.of(keyboardRow));
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(keyboardMarkup);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(CHOOSING_MODE_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage changeBotMode(String username, BotMode botMode, Long chatId) {
        UserEntity user = userService.changeUserBotMode(username, botMode);
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setReplyMarkup(new ReplyKeyboardRemove(true));

        switch (botMode) {
            case ALL -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ALL_TEXT, user.getBotLanguage()));
            case ONLY_AN_124_FLIGHTS -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ONLY_AN_124_TEXT, user.getBotLanguage()));
            case ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ONLY_WIDE_BODY_TEXT, user.getBotLanguage()));
        }

        return message;
    }

    @Override
    public SendMessage getBotMode(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);

        switch (user.getBotMode()) {
            case ALL -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ALL_TEXT, user.getBotLanguage()));
            case ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ONLY_WIDE_BODY_TEXT, user.getBotLanguage()));
            case ONLY_AN_124_FLIGHTS -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ONLY_AN_124_TEXT, user.getBotLanguage()));
        }

        return message;
    }

    @Override
    public SendMessage changeBotLanguageCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOOSING_LANG);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(LANGUAGES);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(List.of(keyboardRow));
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(keyboardMarkup);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(SELECT_LANGUAGE_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage changeBotLanguage(String username, BotLanguage botLanguage, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOSEN_AIRPORT);
        userService.changeUserLanguage(username, botLanguage);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setReplyMarkup(new ReplyKeyboardRemove(true));
        message.setText(String.format(botMessageUtils.getMessageByLanguage(LANGUAGE_SELECTED_TEXT, user.getBotLanguage()), botLanguage));

        return message;
    }

    @Override
    public SendMessage incorrectBotLanguage(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(botMessageUtils.getMessageByLanguage(INCORRECT_LANGUAGE_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage incorrectModeMessage(String username, String mode, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(INCORRECT_MODE_TEXT, user.getBotLanguage()), mode));
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
