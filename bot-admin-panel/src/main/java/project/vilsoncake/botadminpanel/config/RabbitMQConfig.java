package project.vilsoncake.botadminpanel.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.vilsoncake.botadminpanel.property.RabbitMQProperties;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public Queue feedbackQueue() {
        return new Queue(rabbitMQProperties.getFeedbackQueue(), false);
    }

    @Bean
    public Queue usersStatisticQueue() {
        return new Queue(rabbitMQProperties.getUsersStatisticQueue(), false);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(rabbitMQProperties.getNotificationQueue(), false);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        connectionFactory.setHost(rabbitMQProperties.getConnectionHost());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.declareQueue(feedbackQueue());
        rabbitAdmin.declareQueue(usersStatisticQueue());
        rabbitAdmin.declareQueue(notificationQueue());

        log.info("All RabbitMQ queues added");

        return rabbitAdmin;
    }
}
