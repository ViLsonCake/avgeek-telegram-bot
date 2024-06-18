package project.vilsoncake.telegrambot.bot;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.property.BotProperties;
import project.vilsoncake.telegrambot.service.BotService;

import java.util.ArrayList;
import java.util.List;

import static project.vilsoncake.telegrambot.constant.BotMessageEngConst.CANCEL_ADDING_EMAIL_TRIGGER;
import static project.vilsoncake.telegrambot.constant.CommandConst.*;
import static project.vilsoncake.telegrambot.entity.enumerated.BotLanguage.*;

@Component
public class AvgeekTelegramBot extends AbilityBot {

    private final BotService botService;

    protected AvgeekTelegramBot(BotProperties botProperties, BotService botService) {
        super(botProperties.getToken(), botProperties.getName());
        this.botService = botService;
    }

    @Override
    public long creatorId() {
        return 1L;
    }

    @Override
    public void onRegister() {
        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new BotCommand(CHANGE_AIRPORT_COMMAND_NAME, CHANGE_AIRPORT_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(CURRENT_AIRPORT_COMMAND_NAME, CURRENT_AIRPORT_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(CHANGE_MODE_COMMAND_NAME, CHANGE_MODE_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(CURRENT_MODE_COMMAND_NAME, CURRENT_MODE_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(SET_EMAIL_COMMAND_NAME, SET_EMAIL_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(MY_EMAIL_COMMAND_NAME, MY_EMAIL_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(REMOVE_EMAIL_COMMAND_NAME, REMOVE_EMAIL_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(CHANGE_LANGUAGE_COMMAND_NAME, CHANGE_LANGUAGE_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(START_COMMAND_NAME, START_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(PING_COMMAND_NAME, PING_COMMAND_DESCRIPTION));

        try {
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
            execute(new SetChatMenuButton());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.getMessage().hasText()) {
            return;
        }

        String username = update.getMessage().getChat().getUserName();
        Long chatId = update.getMessage().getChatId();

        try {
            if (update.getMessage().isCommand()) {
                switch (update.getMessage().getText()) {
                    case START_COMMAND_NAME:
                        execute(botService.startBotCommand(username, chatId));
                        break;
                    case PING_COMMAND_NAME:
                        execute(botService.pingCommand(chatId));
                        break;
                    case CHANGE_AIRPORT_COMMAND_NAME:
                        execute(botService.changeUserAirportCommand(username, chatId));
                        break;
                    case CURRENT_AIRPORT_COMMAND_NAME:
                        execute(botService.getUserAirportCommand(username, chatId));
                        break;
                    case CHANGE_MODE_COMMAND_NAME:
                        execute(botService.changeBotModeCommand(username, chatId));
                        break;
                    case CURRENT_MODE_COMMAND_NAME:
                        execute(botService.getBotMode(username, chatId));
                        break;
                    case SET_EMAIL_COMMAND_NAME:
                        execute(botService.setEmailCommand(username, chatId));
                        break;
                    case MY_EMAIL_COMMAND_NAME:
                        execute(botService.myEmailCommand(username, chatId));
                        break;
                    case REMOVE_EMAIL_COMMAND_NAME:
                        execute(botService.removeEmail(username, chatId));
                        break;
                    case CHANGE_LANGUAGE_COMMAND_NAME:
                        execute(botService.changeBotLanguageCommand(username, chatId));
                        break;
                }
                return;
            }

            if (botService.isUserRegistered(username)) {
                switch (botService.getUserState(username)) {
                    case CHOOSING_AIRPORT:
                        String airportCode = update.getMessage().getText().trim().toLowerCase();
                        execute(botService.changeUserAirport(username, airportCode, chatId));
                        break;
                    case CHOOSING_MODE:
                        BotMode botMode;
                        switch (update.getMessage().getText()) {
                            case MODE_ALL_BUTTON_TEXT -> botMode = BotMode.ALL;
                            case MODE_WIDE_BODY_BUTTON_TEXT -> botMode = BotMode.ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS;
                            case MODE_AN_124_BUTTON_TEXT -> botMode = BotMode.ONLY_AN_124_FLIGHTS;
                            default -> {
                                execute(botService.incorrectModeMessage(username, update.getMessage().getText(), chatId));
                                return;
                            }
                        }
                        execute(botService.changeBotMode(username, botMode, chatId));
                        break;
                    case WAIT_FOR_EMAIL:
                        String email = update.getMessage().getText().trim();
                        execute(botService.setEmail(username, email, chatId));
                        break;
                    case WAIT_FOR_EMAIL_CODE:
                        if (update.getMessage().getText().equals(CANCEL_ADDING_EMAIL_TRIGGER)) {
                            execute(botService.cancelEmail(username, chatId));
                        } else {
                            String code = update.getMessage().getText().trim();
                            execute(botService.verifyEmail(username, code, chatId));
                        }
                        break;
                    case CHOOSING_LANG:
                        String language = update.getMessage().getText().trim();
                        BotLanguage botLanguage;

                        if (ENG.name().toLowerCase().equals(language)) {
                            botLanguage = ENG;
                        } else if (RU.name().toLowerCase().equals(language)) {
                            botLanguage = RU;
                        } else if (UA.name().toLowerCase().equals(language)) {
                            botLanguage = UA;
                        } else {
                            execute(botService.incorrectBotLanguage(username, chatId));
                            return;
                        }

                        execute(botService.changeBotLanguage(username, botLanguage, chatId));
                        break;
                }
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
