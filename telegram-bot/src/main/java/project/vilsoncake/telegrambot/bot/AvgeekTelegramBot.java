package project.vilsoncake.telegrambot.bot;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.vilsoncake.telegrambot.property.BotProperties;

import java.util.ArrayList;
import java.util.List;

import static project.vilsoncake.telegrambot.constant.CommandDescriptionsEngConst.*;
import static project.vilsoncake.telegrambot.constant.CommandDescriptionsRuConst.*;
import static project.vilsoncake.telegrambot.constant.CommandDescriptionsUkConst.*;
import static project.vilsoncake.telegrambot.constant.CommandNamesConst.*;

@Component
public class AvgeekTelegramBot extends AbilityBot {

    private final BotMessageHandler botMessageHandler;
    private final BotProperties botProperties;

    protected AvgeekTelegramBot(BotProperties botProperties, BotMessageHandler botMessageHandler) {
        super(botProperties.getToken(), botProperties.getName());
        this.botMessageHandler = botMessageHandler;
        this.botProperties = botProperties;
    }

    @Override
    public long creatorId() {
        return botProperties.getCreatorId();
    }

    @Override
    public void onRegister() {
        List<BotCommand> botCommandsEng = new ArrayList<>();
        botCommandsEng.add(new BotCommand(CHANGE_AIRPORT_COMMAND_NAME, CHANGE_AIRPORT_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(CURRENT_AIRPORT_COMMAND_NAME, CURRENT_AIRPORT_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(CHANGE_MODE_COMMAND_NAME, CHANGE_MODE_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(CURRENT_MODE_COMMAND_NAME, CURRENT_MODE_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(SELECT_AIRCRAFT_COMMAND_NAME, CHOOSE_AIRCRAFT_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(CHOSEN_AIRCRAFT_COMMAND_NAME, CHOSEN_AIRCRAFT_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(CHANGE_UNITS_COMMAND_NAME, CHANGE_UNITS_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(CURRENT_UNITS_COMMAND_NAME, CURRENT_UNITS_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(FEEDBACK_COMMAND_NAME, FEEDBACK_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(CHANGE_LANGUAGE_COMMAND_NAME, CHANGE_LANGUAGE_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(SET_EMAIL_COMMAND_NAME, SET_EMAIL_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(MY_EMAIL_COMMAND_NAME, MY_EMAIL_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(REMOVE_EMAIL_COMMAND_NAME, REMOVE_EMAIL_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(START_COMMAND_NAME, START_COMMAND_ENG_DESCRIPTION));
        botCommandsEng.add(new BotCommand(PING_COMMAND_NAME, PING_COMMAND_ENG_DESCRIPTION));

        List<BotCommand> botCommandsRu = new ArrayList<>();
        botCommandsRu.add(new BotCommand(CHANGE_AIRPORT_COMMAND_NAME, CHANGE_AIRPORT_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(CURRENT_AIRPORT_COMMAND_NAME, CURRENT_AIRPORT_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(CHANGE_MODE_COMMAND_NAME, CHANGE_MODE_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(CURRENT_MODE_COMMAND_NAME, CURRENT_MODE_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(SELECT_AIRCRAFT_COMMAND_NAME, CHOOSE_AIRCRAFT_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(CHOSEN_AIRCRAFT_COMMAND_NAME, CHOSEN_AIRCRAFT_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(CHANGE_UNITS_COMMAND_NAME, CHANGE_UNITS_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(CURRENT_UNITS_COMMAND_NAME, CURRENT_UNITS_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(FEEDBACK_COMMAND_NAME, FEEDBACK_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(CHANGE_LANGUAGE_COMMAND_NAME, CHANGE_LANGUAGE_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(SET_EMAIL_COMMAND_NAME, SET_EMAIL_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(MY_EMAIL_COMMAND_NAME, MY_EMAIL_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(REMOVE_EMAIL_COMMAND_NAME, REMOVE_EMAIL_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(START_COMMAND_NAME, START_COMMAND_RU_DESCRIPTION));
        botCommandsRu.add(new BotCommand(PING_COMMAND_NAME, PING_COMMAND_RU_DESCRIPTION));

        List<BotCommand> botCommandsUk = new ArrayList<>();
        botCommandsUk.add(new BotCommand(CHANGE_AIRPORT_COMMAND_NAME, CHANGE_AIRPORT_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(CURRENT_AIRPORT_COMMAND_NAME, CURRENT_AIRPORT_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(CHANGE_MODE_COMMAND_NAME, CHANGE_MODE_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(CURRENT_MODE_COMMAND_NAME, CURRENT_MODE_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(SELECT_AIRCRAFT_COMMAND_NAME, CHOOSE_AIRCRAFT_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(CHOSEN_AIRCRAFT_COMMAND_NAME, CHOSEN_AIRCRAFT_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(CHANGE_UNITS_COMMAND_NAME, CHANGE_UNITS_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(CURRENT_UNITS_COMMAND_NAME, CURRENT_UNITS_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(FEEDBACK_COMMAND_NAME, FEEDBACK_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(CHANGE_LANGUAGE_COMMAND_NAME, CHANGE_LANGUAGE_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(SET_EMAIL_COMMAND_NAME, SET_EMAIL_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(MY_EMAIL_COMMAND_NAME, MY_EMAIL_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(REMOVE_EMAIL_COMMAND_NAME, REMOVE_EMAIL_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(START_COMMAND_NAME, START_COMMAND_UK_DESCRIPTION));
        botCommandsUk.add(new BotCommand(PING_COMMAND_NAME, PING_COMMAND_UK_DESCRIPTION));

        try {
            execute(new SetMyCommands(botCommandsEng, new BotCommandScopeDefault(), null));
            execute(new SetMyCommands(botCommandsRu, new BotCommandScopeDefault(), RU_LANGUAGE_CODE));
            execute(new SetMyCommands(botCommandsUk, new BotCommandScopeDefault(), UK_LANGUAGE_CODE));
            execute(new SetChatMenuButton());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        botMessageHandler.handleMessage(update, this);
    }
}
