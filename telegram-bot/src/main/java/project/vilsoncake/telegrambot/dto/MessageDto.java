package project.vilsoncake.telegrambot.dto;

import lombok.Data;
import project.vilsoncake.telegrambot.entity.enumerated.CustomMessageMode;

@Data
public class MessageDto {
    private String englishText;
    private String russianText;
    private String ukrainianText;
    private CustomMessageMode mode;
}
