package project.vilsoncake.botadminpanel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import project.vilsoncake.botadminpanel.dto.UserStatisticDto;
import project.vilsoncake.botadminpanel.service.RabbitMQService;
import project.vilsoncake.botadminpanel.service.UserStatisticService;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService {

    private final UserStatisticService userStatisticService;

    @RabbitListener(queues = "${rabbitmq.users-statistic-queue}")
    @Override
    public void consumeUserStatisticMessage(UserStatisticDto userStatisticDto) {
        userStatisticService.addNewUserStatistic(userStatisticDto);
    }
}
