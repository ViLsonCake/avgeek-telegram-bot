package project.vilsoncake.telegrambot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.vilsoncake.telegrambot.dto.UserStatisticDto;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.entity.enumerated.UnitsSystem;
import project.vilsoncake.telegrambot.rabbitmq.producer.RabbitMQProducer;
import project.vilsoncake.telegrambot.service.BotService;

import static project.vilsoncake.telegrambot.constant.BotMessageEngConst.CANCEL_ADDING_EMAIL_TRIGGER;
import static project.vilsoncake.telegrambot.constant.CommandNamesConst.*;
import static project.vilsoncake.telegrambot.entity.enumerated.BotLanguage.*;

@Component
@RequiredArgsConstructor
public class BotMessageHandler {

    private final BotService botService;
    private final RabbitMQProducer rabbitMQProducer;

    public void handleMessage(Update update, AvgeekTelegramBot bot) {
        if (!update.getMessage().hasText()) {
            return;
        }

        String username = update.getMessage().getChat().getUserName();

        if (username == null) {
            username = update.getMessage().getFrom().getId().toString();
        }

        String languageCode = update.getMessage().getFrom().getLanguageCode();
        Long chatId = update.getMessage().getChatId();

        try {
            if (update.getMessage().isCommand()) {

                UserStatisticDto userStatisticDto = new UserStatisticDto(username, update.getMessage().getText());
                rabbitMQProducer.sendUserStatistic(userStatisticDto);

                switch (update.getMessage().getText()) {
                    case START_COMMAND_NAME:
                        bot.execute(botService.startBotCommand(username, languageCode, chatId));
                        break;
                    case PING_COMMAND_NAME:
                        bot.execute(botService.pingCommand(chatId));
                        break;
                    case CHANGE_AIRPORT_COMMAND_NAME:
                        bot.execute(botService.changeUserAirportCommand(username, chatId));
                        break;
                    case CURRENT_AIRPORT_COMMAND_NAME:
                        bot.execute(botService.getUserAirportCommand(username, chatId));
                        break;
                    case CHANGE_MODE_COMMAND_NAME:
                        bot.execute(botService.changeBotModeCommand(username, chatId));
                        break;
                    case CURRENT_MODE_COMMAND_NAME:
                        bot.execute(botService.getBotMode(username, chatId));
                        break;
                    case CHANGE_UNITS_COMMAND_NAME:
                        bot.execute(botService.changeUnitsCommand(username, chatId));
                        break;
                    case CURRENT_UNITS_COMMAND_NAME:
                        bot.execute(botService.getUserUnitsSystem(username, chatId));
                        break;
                    case SET_EMAIL_COMMAND_NAME:
                        bot.execute(botService.setEmailCommand(username, chatId));
                        break;
                    case MY_EMAIL_COMMAND_NAME:
                        bot.execute(botService.myEmailCommand(username, chatId));
                        break;
                    case REMOVE_EMAIL_COMMAND_NAME:
                        bot.execute(botService.removeEmail(username, chatId));
                        break;
                    case CHANGE_LANGUAGE_COMMAND_NAME:
                        bot.execute(botService.changeBotLanguageCommand(username, chatId));
                        break;
                }
                return;
            }

            if (botService.isUserRegistered(username)) {
                switch (botService.getUserState(username)) {
                    case CHOOSING_AIRPORT:
                        String airportCode = update.getMessage().getText().trim().toLowerCase();
                        bot.execute(botService.changeUserAirport(username, airportCode, chatId));
                        break;
                    case CHOOSING_MODE:
                        BotMode botMode;
                        switch (update.getMessage().getText()) {
                            case MODE_ALL_BUTTON_TEXT -> botMode = BotMode.ALL;
                            case MODE_WIDE_BODY_BUTTON_TEXT -> botMode = BotMode.ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS;
                            case MODE_AN_124_BUTTON_TEXT -> botMode = BotMode.ONLY_AN_124_FLIGHTS;
                            case MODE_MUTE_TEXT -> botMode = BotMode.MUTE;
                            default -> {
                                bot.execute(botService.incorrectModeMessage(username, update.getMessage().getText(), chatId));
                                return;
                            }
                        }
                        bot.execute(botService.changeBotMode(username, botMode, chatId));
                        break;
                    case WAIT_FOR_EMAIL:
                        String email = update.getMessage().getText().trim();
                        bot.execute(botService.setEmail(username, email, chatId));
                        break;
                    case WAIT_FOR_EMAIL_CODE:
                        if (update.getMessage().getText().equals(CANCEL_ADDING_EMAIL_TRIGGER)) {
                            bot.execute(botService.cancelEmail(username, chatId));
                        } else {
                            String code = update.getMessage().getText().trim();
                            bot.execute(botService.verifyEmail(username, code, chatId));
                        }
                        break;
                    case CHOOSING_LANG:
                        String language = update.getMessage().getText().trim();
                        BotLanguage botLanguage;

                        if (ENG.name().toLowerCase().equals(language)) {
                            botLanguage = ENG;
                        } else if (RU.name().toLowerCase().equals(language)) {
                            botLanguage = RU;
                        } else if (UK.name().toLowerCase().equals(language)) {
                            botLanguage = UK;
                        } else {
                            bot.execute(botService.incorrectBotLanguage(username, chatId));
                            return;
                        }

                        bot.execute(botService.changeBotLanguage(username, botLanguage, chatId));
                        break;
                    case CHOOSING_UNITS:
                        UnitsSystem unitsSystem;
                        switch (update.getMessage().getText().trim()) {
                            case METRIC_UNITS_BUTTON_TEXT -> unitsSystem = UnitsSystem.METRIC;
                            case IMPERIAL_UNITS_BUTTON_TEXT -> unitsSystem = UnitsSystem.IMPERIAL;
                            case AVIATION_UNITS_BUTTON_TEXT -> unitsSystem = UnitsSystem.AVIATION;
                            default -> {
                                bot.execute(botService.incorrectUnitsSystem(username, chatId));
                                return;
                            }
                        }

                        bot.execute(botService.changeUnits(username, unitsSystem, chatId));
                        break;
                }
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
