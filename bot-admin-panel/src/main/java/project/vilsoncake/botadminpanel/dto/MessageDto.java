package project.vilsoncake.botadminpanel.dto;

import lombok.Data;
import project.vilsoncake.botadminpanel.entity.document.MessageDocument;
import project.vilsoncake.botadminpanel.entity.enumerated.CustomMessageMode;

import java.util.Date;
import java.util.UUID;

@Data
public class MessageDto {
    private String englishText;
    private String russianText;
    private String ukrainianText;
    private CustomMessageMode mode;

    public static MessageDocument toDocument(MessageDto messageDto) {
        MessageDocument messageDocument = new MessageDocument();
        messageDocument.setId(UUID.randomUUID());
        messageDocument.setCreatedAt(new Date());
        messageDocument.setEnglishText(messageDto.getEnglishText());
        messageDocument.setRussianText(messageDto.getRussianText());
        messageDocument.setUkrainianText(messageDocument.getUkrainianText());
        messageDocument.setMode(messageDto.getMode().toString());

        return messageDocument;
    }
}
