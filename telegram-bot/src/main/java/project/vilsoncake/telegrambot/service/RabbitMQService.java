package project.vilsoncake.telegrambot.service;

import project.vilsoncake.telegrambot.dto.UserStatisticDto;

public interface RabbitMQService {
    boolean sendUserStatistic(UserStatisticDto userStatisticDto);
}
