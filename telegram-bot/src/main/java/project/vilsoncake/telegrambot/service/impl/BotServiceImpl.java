package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import project.vilsoncake.telegrambot.bot.BaseBotMessage;
import project.vilsoncake.telegrambot.dto.AircraftFamilyDto;
import project.vilsoncake.telegrambot.dto.AirportCodesDto;
import project.vilsoncake.telegrambot.dto.AirportDto;
import project.vilsoncake.telegrambot.dto.MessageDto;
import project.vilsoncake.telegrambot.entity.AircraftEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.*;
import project.vilsoncake.telegrambot.exception.AirportNotFoundException;
import project.vilsoncake.telegrambot.service.AircraftService;
import project.vilsoncake.telegrambot.service.BotService;
import project.vilsoncake.telegrambot.service.MailService;
import project.vilsoncake.telegrambot.service.UserService;
import project.vilsoncake.telegrambot.utils.*;

import java.util.ArrayList;
import java.util.List;

import static project.vilsoncake.telegrambot.constant.BotMessageEngConst.PING_COMMAND_TEXT;
import static project.vilsoncake.telegrambot.constant.CommandNamesConst.*;
import static project.vilsoncake.telegrambot.constant.NumberConst.MAX_SELECTED_AIRCRAFT_COUNT;
import static project.vilsoncake.telegrambot.entity.enumerated.BotMessageTemplate.*;
import static project.vilsoncake.telegrambot.entity.enumerated.CustomMessageMode.ONLY_NOT_SELECTED;
import static project.vilsoncake.telegrambot.entity.enumerated.CustomMessageMode.ONLY_SELECTED;
import static project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate.CODE_MESSAGE_SUBJECT;
import static project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate.CODE_MESSAGE_TEXT;
import static project.vilsoncake.telegrambot.entity.enumerated.UserState.*;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final UserService userService;
    private final AircraftService aircraftService;
    private final MailService mailService;
    private final VerifyUtils verifyUtils;
    private final AirportsUtils airportsUtils;
    private final BotMessageUtils botMessageUtils;
    private final MailMessageUtils mailMessageUtils;
    private final AircraftUtils aircraftUtils;
    private final List<AircraftFamilyDto> aircraft;

    @Override
    public SendMessage pingCommand(Long chatId) {
        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(PING_COMMAND_TEXT);

        return message;
    }

    @Override
    public SendMessage startBotCommand(String username, String languageCode, Long chatId) {
        UserEntity user;
        if (userService.isUserExistsByUsername(username)) {
            user = userService.changeUserState(username, CHOOSING_AIRPORT);
        } else {
            user = new UserEntity(username, chatId, CHOOSING_AIRPORT);

            switch (languageCode) {
                case RU_LANGUAGE_CODE -> user.setBotLanguage(BotLanguage.RU);
                case UK_LANGUAGE_CODE -> user.setBotLanguage(BotLanguage.UK);
                default -> user.setBotLanguage(BotLanguage.ENG);
            }

            userService.addNewUser(user);
        }

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(START_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage changeUserAirportCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOOSING_AIRPORT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(CHANGE_AIRPORT_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage getUserAirportCommand(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        if (user.getState().equals(CHOOSING_AIRPORT)) {
            SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
            message.setText(botMessageUtils.getMessageByLanguage(USER_CANNOT_CHOOSE_AIRPORT_TEXT, user.getBotLanguage()));

            return message;
        }

        String airportCode = userService.getUserByUsername(username).getAirport();

        AirportDto airportDto = airportsUtils.getAirportByIataCode(airportCode);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(CURRENT_AIRPORT_TEXT, user.getBotLanguage()), airportDto.getName(), airportCode, airportCode));

        return message;
    }

    @Override
    public SendMessage changeUserAirport(String username, String code, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        AirportCodesDto airportCodesDto;
        try {
            airportCodesDto = airportsUtils.validateAirportCode(code);
        } catch (AirportNotFoundException e) {
            SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
            message.setText(String.format(botMessageUtils.getMessageByLanguage(INVALID_AIRPORT_CODE_TEXT, user.getBotLanguage()), code));

            return message;
        }

        AirportDto airportDto = airportsUtils.getAirportByIataCode(airportCodesDto.getIata());

        userService.changeUserAirport(username, airportCodesDto.getIata());
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(CHOOSE_AIRPORT_TEXT, user.getBotLanguage()), airportDto.getName()));

        return message;
    }

    @Override
    public SendMessage setEmailCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, WAIT_FOR_EMAIL);
        userService.changeUserEmailVerified(username, false);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(WAIT_FOR_EMAIL_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage myEmailCommand(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);

        if (user.getEmail() == null) {
            message.setText(botMessageUtils.getMessageByLanguage(USER_NOT_ADDED_EMAIL_TEXT, user.getBotLanguage()));
            return message;
        }
        if (!user.isEmailVerified()) {
            message.setText(String.format(botMessageUtils.getMessageByLanguage(EMAIL_NOT_VERIFY_EMAIL, user.getBotLanguage()), user.getEmail()));
            return message;
        }

        message.setText(String.format(botMessageUtils.getMessageByLanguage(MY_EMAIL_TEXT, user.getBotLanguage()), user.getEmail()));
        return message;
    }

    @Override
    public SendMessage setEmail(String username, String email, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        if (!verifyUtils.isEmailValid(email)) {
            SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
            message.setText(botMessageUtils.getMessageByLanguage(INVALID_EMAIL_TEXT, user.getBotLanguage()));

            return message;
        }

        userService.changeUserEmail(username, email);
        userService.changeUserState(username, WAIT_FOR_EMAIL_CODE);

        int code = verifyUtils.generateCode();

        mailService.sendMessage(email, mailMessageUtils.getMessageByLanguage(CODE_MESSAGE_SUBJECT, user.getBotLanguage()), String.format(mailMessageUtils.getMessageByLanguage(CODE_MESSAGE_TEXT, user.getBotLanguage()), code));

        userService.changeUserEmailCode(username, code);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(WAIT_FOR_CODE_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage removeEmail(String username, Long chatId) {
        UserEntity user = userService.changeUserEmail(username, null);
        userService.changeUserEmailVerified(username, false);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(REMOVE_EMAIL_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage cancelEmail(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(CANCEL_ADDING_EMAIL_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage verifyEmail(String username, String code, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        if (!String.valueOf(user.getEmailCode()).equals(code)) {
            SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
            message.setText(botMessageUtils.getMessageByLanguage(INCORRECT_EMAIL_VERIFY_CODE, user.getBotLanguage()));
            return message;
        }

        userService.changeUserState(username, CHOSEN_AIRPORT);
        userService.changeUserEmailVerified(username, true);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
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

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setReplyMarkup(keyboardMarkup);
        message.setText(botMessageUtils.getMessageByLanguage(CHOOSING_MODE_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage changeBotMode(String username, BotMode botMode, Long chatId) {
        UserEntity user = userService.changeUserBotMode(username, botMode);
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setReplyMarkup(new ReplyKeyboardRemove(true));

        switch (botMode) {
            case ALL -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ALL_TEXT, user.getBotLanguage()));
            case ONLY_AN_124_FLIGHTS -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ONLY_AN_124_TEXT, user.getBotLanguage()));
            case ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ONLY_WIDE_BODY_TEXT, user.getBotLanguage()));
            case MUTE -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_MUTE_TEXT, user.getBotLanguage()));
        }

        return message;
    }

    @Override
    public SendMessage getBotMode(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);

        switch (user.getBotMode()) {
            case ALL -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ALL_TEXT, user.getBotLanguage()));
            case ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ONLY_WIDE_BODY_TEXT, user.getBotLanguage()));
            case ONLY_AN_124_FLIGHTS -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_ONLY_AN_124_TEXT, user.getBotLanguage()));
            case MUTE -> message.setText(botMessageUtils.getMessageByLanguage(CHOSEN_MODE_MUTE_TEXT, user.getBotLanguage()));
        }

        return message;
    }

    @Override
    public SendMessage chooseAircraftCommand(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        aircraftService.deleteAircraftFromUser(user);
        userService.changeUserState(username, CHOOSING_AIRCRAFT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);

        message.setText(botMessageUtils.getMessageByLanguage(CHOOSE_AIRCRAFT_TEXT, user.getBotLanguage()));
        message.setReplyMarkup(aircraftUtils.getAircraftButtons(aircraft));

        return message;
    }

    @Transactional
    @Override
    public SendMessage chooseAircraft(String username, AircraftFamily aircraftFamily, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        AircraftEntity aircraftEntity = new AircraftEntity(aircraftFamily, user);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);

        String aircraftName = aircraftUtils.getAircraftNameByCallback(aircraft, aircraftFamily);

        if (!aircraftService.addAircraftToUser(aircraftEntity)) {
            message.setText(String.format(botMessageUtils.getMessageByLanguage(AIRCRAFT_ALREADY_CHOSEN_TEXT, user.getBotLanguage()), aircraftName));
            return message;
        }

        int selectedAircraftCount = aircraftService.findAllAircraftByUser(user).size();

        if (selectedAircraftCount == MAX_SELECTED_AIRCRAFT_COUNT) {
            userService.changeUserState(username, CHOSEN_AIRPORT);
            message.setText(String.format(botMessageUtils.getMessageByLanguage(CHOSEN_MAX_AIRCRAFT_COUNT_TEXT, user.getBotLanguage()), aircraftUtils.getUserAircraftList(user.getAircraft(), aircraft)));
            return message;
        }

        message.setText(String.format(botMessageUtils.getMessageByLanguage(CHOSEN_AIRCRAFT_TEXT, user.getBotLanguage()), aircraftName, MAX_SELECTED_AIRCRAFT_COUNT - selectedAircraftCount));
        return message;
    }

    @Transactional
    @Override
    public SendMessage chosenAircraftCommand(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(CHOSEN_AIRCRAFT_COUNT_TEXT, user.getBotLanguage()), aircraftUtils.getUserAircraftList(user.getAircraft(), aircraft)));
        return message;
    }

    @Transactional
    @Override
    public SendMessage acceptAircraft(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(CHOSEN_AIRCRAFT_COUNT_TEXT, user.getBotLanguage()), aircraftUtils.getUserAircraftList(user.getAircraft(), aircraft)));
        return message;
    }

    @Override
    public SendMessage cancelAircraft(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        aircraftService.deleteAircraftFromUser(user);
        userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(CANCEL_CHOOSING_AIRCRAFT_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage changeBotLanguageCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOOSING_LANG);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(LANGUAGES);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(List.of(keyboardRow));
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setReplyMarkup(keyboardMarkup);
        message.setText(botMessageUtils.getMessageByLanguage(SELECT_LANGUAGE_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage changeBotLanguage(String username, BotLanguage botLanguage, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOSEN_AIRPORT);
        userService.changeUserLanguage(username, botLanguage);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setReplyMarkup(new ReplyKeyboardRemove(true));
        message.setText(String.format(botMessageUtils.getMessageByLanguage(LANGUAGE_SELECTED_TEXT, user.getBotLanguage()), botLanguage));

        return message;
    }

    @Override
    public SendMessage incorrectBotLanguage(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(INCORRECT_LANGUAGE_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage incorrectModeMessage(String username, String mode, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(String.format(botMessageUtils.getMessageByLanguage(INCORRECT_MODE_TEXT, user.getBotLanguage()), mode));

        return message;
    }

    @Override
    public SendMessage changeUnitsCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOOSING_UNITS);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(UNITS);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(List.of(keyboardRow));
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setReplyMarkup(keyboardMarkup);
        message.setText(botMessageUtils.getMessageByLanguage(CHANGE_UNITS_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage changeUnits(String username, UnitsSystem unitsSystem, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOSEN_AIRPORT);
        userService.changeUserUnitsSystem(username, unitsSystem);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setReplyMarkup(new ReplyKeyboardRemove(true));

        switch (unitsSystem) {
            case METRIC -> message.setText(botMessageUtils.getMessageByLanguage(CURRENT_METRIC_UNITS_TEXT, user.getBotLanguage()));
            case IMPERIAL -> message.setText(botMessageUtils.getMessageByLanguage(CURRENT_IMPERIAL_UNITS_TEXT, user.getBotLanguage()));
            case AVIATION -> message.setText(botMessageUtils.getMessageByLanguage(CURRENT_AVIATION_UNITS_TEXT, user.getBotLanguage()));
        }

        return message;
    }

    @Override
    public SendMessage getUserUnitsSystem(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);

        switch (user.getUnitsSystem()) {
            case METRIC -> message.setText(botMessageUtils.getMessageByLanguage(CURRENT_METRIC_UNITS_TEXT, user.getBotLanguage()));
            case IMPERIAL -> message.setText(botMessageUtils.getMessageByLanguage(CURRENT_IMPERIAL_UNITS_TEXT, user.getBotLanguage()));
            case AVIATION -> message.setText(botMessageUtils.getMessageByLanguage(CURRENT_AVIATION_UNITS_TEXT, user.getBotLanguage()));
        }

        return message;
    }

    @Override
    public SendMessage incorrectUnitsSystem(String username, Long chatId) {
        UserEntity user = userService.getUserByUsername(username);
        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(INCORRECT_UNITS_TEXT, user.getBotLanguage()));

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

    @Override
    public List<SendMessage> sendCustomMessage(MessageDto messageDto) {
        List<UserEntity> users = userService.findAllUsers();
        List<SendMessage> messages = new ArrayList<>();

        for (UserEntity user : users) {
            if (user.getBotMode().equals(BotMode.MUTE)) {
                continue;
            }

            if (messageDto.getMode().equals(ONLY_SELECTED) && user.getAirport() == null) {
                continue;
            }

            if (messageDto.getMode().equals(ONLY_NOT_SELECTED) && user.getAirport() != null) {
                continue;
            }

            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());

            switch (user.getBotLanguage()) {
                case ENG -> message.setText(messageDto.getEnglishText());
                case RU -> message.setText(messageDto.getRussianText());
                case UK -> message.setText(messageDto.getUkrainianText());
            }

            messages.add(message);
        }

        return messages;
    }

    @Override
    public SendMessage feedbackCommand(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, SENDING_FEEDBACK);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(FEEDBACK_COMMAND_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage feedbackSent(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(FEEDBACK_SENT_TEXT, user.getBotLanguage()));

        return message;
    }

    @Override
    public SendMessage cancelFeedback(String username, Long chatId) {
        UserEntity user = userService.changeUserState(username, CHOSEN_AIRPORT);

        SendMessage message = BaseBotMessage.getBaseBotMessage(chatId);
        message.setText(botMessageUtils.getMessageByLanguage(CANCEL_FEEDBACK_SEND_TEXT, user.getBotLanguage()));

        return message;
    }
}
