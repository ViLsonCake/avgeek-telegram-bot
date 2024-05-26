package project.vilsoncake.telegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;

public interface BotService {
    SendMessage startBotCommand(String username, Long chatId);
    SendMessage changeUserAirportCommand(String username, String code, Long chatId);
    SendMessage changeUserAirport(String username, String code);
    UserState getUserState(String username);
    boolean isUserRegistered(String username);
}
