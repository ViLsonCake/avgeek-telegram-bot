package project.vilsoncake.telegrambot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {
    private String username;
    private String password;
    private String connectionHost;
    private String feedbackQueue;
    private String usersStatisticQueue;
    private String notificationQueue;
}
