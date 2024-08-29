package project.vilsoncake.botadminpanel.service;

import project.vilsoncake.botadminpanel.dto.UserStatisticDto;

public interface RabbitMQService {
    void consumeUserStatisticMessage(UserStatisticDto userStatisticDto);
}
