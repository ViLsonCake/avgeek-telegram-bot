package project.vilsoncake.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableScheduling
public class TelegramBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TelegramBotApplication.class, args);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(context.getBean("avgeekTelegramBot", AbilityBot.class));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
