package project.vilsoncake.botadminpanel.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.vilsoncake.botadminpanel.dto.MessageDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.notification-queue}")
    private String notificationQueue;

    public boolean sendMessage(MessageDto messageDto) {
        amqpTemplate.convertAndSend(notificationQueue, messageDto);
        log.info("Custom message sent");
        return true;
    }
}
