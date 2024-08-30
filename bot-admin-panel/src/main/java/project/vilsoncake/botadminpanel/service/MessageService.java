package project.vilsoncake.botadminpanel.service;

import org.springframework.ui.Model;
import project.vilsoncake.botadminpanel.dto.MessageDto;

import java.util.Map;

public interface MessageService {
    String getMessagesPage(Model model);
    Map<String, String> sendMessage(MessageDto messageDto);
}
