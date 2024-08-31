package project.vilsoncake.telegrambot.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.vilsoncake.telegrambot.dto.UserStatisticDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.users-statistic-queue}")
    private String usersStatisticQueue;

    public boolean sendUserStatistic(UserStatisticDto userStatisticDto) {
        amqpTemplate.convertAndSend(usersStatisticQueue, userStatisticDto);
        log.info("Users statistic message sent");
        return true;
    }
}