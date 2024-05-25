package project.vilsoncake.telegrambot.bot;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.vilsoncake.telegrambot.property.BotProperties;

import java.util.List;

@Component
public class AvgeekTelegramBot extends AbilityBot {

    protected AvgeekTelegramBot(BotProperties botProperties) {
        super(botProperties.getToken(), botProperties.getName());
    }

    @Override
    public long creatorId() {
        return 1L;
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        Update update = updates.get(0);

        if (update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setText(update.getMessage().getText());
            message.setChatId(update.getMessage().getChatId());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
