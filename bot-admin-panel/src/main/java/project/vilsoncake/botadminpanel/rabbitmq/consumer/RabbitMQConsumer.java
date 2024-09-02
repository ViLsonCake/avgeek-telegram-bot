package project.vilsoncake.botadminpanel.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import project.vilsoncake.botadminpanel.dto.FeedbackDto;
import project.vilsoncake.botadminpanel.dto.UserStatisticDto;
import project.vilsoncake.botadminpanel.service.FeedbackService;
import project.vilsoncake.botadminpanel.service.UserStatisticService;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final UserStatisticService userStatisticService;
    private final FeedbackService feedbackService;

    @RabbitListener(queues = "${rabbitmq.users-statistic-queue}")
    public void consumeUserStatisticMessage(UserStatisticDto userStatisticDto) {
        userStatisticService.addNewUserStatistic(userStatisticDto);
    }

    @RabbitListener(queues = "${rabbitmq.feedback-queue}")
    public void consumeUserFeedback(FeedbackDto feedbackDto) {
        feedbackService.addNewFeedback(feedbackDto);
    }
}
