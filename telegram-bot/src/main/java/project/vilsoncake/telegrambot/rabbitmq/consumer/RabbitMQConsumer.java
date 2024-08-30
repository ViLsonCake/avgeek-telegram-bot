package project.vilsoncake.telegrambot.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.telegrambot.bot.BotSender;
import project.vilsoncake.telegrambot.dto.MessageDto;
import project.vilsoncake.telegrambot.service.BotService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final BotService botService;
    private final BotSender botSender;

    @RabbitListener(queues = "${rabbitmq.notification-queue}")
    public void consumeMessage(MessageDto messageDto) {
        List<SendMessage> messages = botService.sendCustomMessage(messageDto);

        for (SendMessage message : messages) {
            botSender.sendMessage(message);
        }

        log.info("Custom message send to users");
    }
}
