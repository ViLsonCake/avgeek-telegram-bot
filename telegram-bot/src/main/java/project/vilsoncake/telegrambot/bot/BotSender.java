package project.vilsoncake.telegrambot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotSender {

    private final AbsSender absSender;

    public boolean sendMessage(SendMessage message) {
        try {
            absSender.execute(message);
            return true;
        } catch (TelegramApiException e) {
            log.warn("User with chat id {} blocked the bot", message.getChatId());
            return false;
        }
    }
}
