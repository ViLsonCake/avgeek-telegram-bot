package project.vilsoncake.botadminpanel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.vilsoncake.botadminpanel.dto.MessageDto;
import project.vilsoncake.botadminpanel.entity.document.MessageDocument;
import project.vilsoncake.botadminpanel.rabbitmq.producer.RabbitMQProducer;
import project.vilsoncake.botadminpanel.repository.MessageRepository;
import project.vilsoncake.botadminpanel.service.MessageService;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Value("${github.repository-link}")
    private String repositoryLink;

    @Override
    public String getMessagesPage(Model model) {
        model.addAttribute("repositoryLink", repositoryLink);
        return "messages.html";
    }

    @Override
    public Map<String, String> sendMessage(MessageDto messageDto) {
        MessageDocument messageDocument = MessageDto.toDocument(messageDto);
        messageRepository.save(messageDocument);

        rabbitMQProducer.sendMessage(messageDto);

        return Map.of("message", "Message sent");
    }
}
