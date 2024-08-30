package project.vilsoncake.botadminpanel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.botadminpanel.dto.MessageDto;
import project.vilsoncake.botadminpanel.service.MessageService;

import java.util.Map;

@Controller
@RequestMapping("/custom-messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public String getMessagesPage(Model model) {
        return messageService.getMessagesPage(model);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody MessageDto messageDto) {
        return ResponseEntity.ok(messageService.sendMessage(messageDto));
    }
}
