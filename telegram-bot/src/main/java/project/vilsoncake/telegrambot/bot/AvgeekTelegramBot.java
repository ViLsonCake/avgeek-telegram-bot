package project.vilsoncake.telegrambot.bot;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;
import project.vilsoncake.telegrambot.property.BotProperties;
import project.vilsoncake.telegrambot.service.BotService;

import java.util.List;

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
    public void onUpdatesReceived(List<Update> updates) {
        Update update = updates.get(0);

        if (!update.getMessage().hasText()) {
            return;
        }

        String username = update.getMessage().getChat().getUserName();
        Long chatId = update.getMessage().getChatId();

        try {
            if (update.getMessage().getText().equals("/start")) {
                execute(botService.startBotCommand(username, chatId));
                return;
            } else if (update.getMessage().getText().equals("/ping")) {
                execute(botService.pingCommand(chatId));
                return;
            } else if (update.getMessage().getText().equals("/changeairport")) {
                execute(botService.changeUserAirportCommand(username, chatId));
                return;
            } else if (update.getMessage().getText().equals("/currentairport")) {
                execute(botService.getUserAirportCommand(username, chatId));
                return;
            }

            if (botService.isUserRegistered(username)) {
                if (botService.getUserState(username).equals(UserState.CHOOSING_AIRPORT)) {
                    String airportCode = update.getMessage().getText().trim().toLowerCase();
                    execute(botService.changeUserAirport(username, airportCode, chatId));
                }
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
