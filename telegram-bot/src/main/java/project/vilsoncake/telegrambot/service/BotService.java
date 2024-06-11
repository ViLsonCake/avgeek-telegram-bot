package project.vilsoncake.telegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;

public interface BotService {
    SendMessage pingCommand(Long chatId);
    SendMessage startBotCommand(String username, Long chatId);
    SendMessage changeUserAirportCommand(String username, Long chatId);
    SendMessage getUserAirportCommand(String username, Long chatId);
    SendMessage changeUserAirport(String username, String code, Long chatId);
    SendMessage setEmailCommand(String username, Long chatId);
    SendMessage myEmailCommand(String username, Long chatId);
    SendMessage setEmail(String username, String email, Long chatId);
    SendMessage removeEmail(String username, Long chatId);
    SendMessage cancelEmail(String username, Long chatId);
    SendMessage verifyEmail(String username, String code, Long chatId);
    SendMessage changeBotModeCommand(String username, Long chatId);
    SendMessage changeBotMode(String username, BotMode botMode, Long chatId);
    SendMessage getBotMode(String username, Long chatId);
    SendMessage changeBotLanguageCommand(String username, Long chatId);
    SendMessage changeBotLanguage(String username, BotLanguage botLanguage, Long chatId);
    SendMessage incorrectBotLanguage(String username, Long chatId);
    SendMessage incorrectModeMessage(String username, String mode, Long chatId);
    UserState getUserState(String username);
    boolean isUserRegistered(String username);
}
