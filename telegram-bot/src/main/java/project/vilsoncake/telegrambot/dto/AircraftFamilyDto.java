package project.vilsoncake.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.telegrambot.entity.enumerated.AircraftFamily;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftFamilyDto {

    @JsonAlias("callback_data")
    private AircraftFamily callbackData;

    @JsonAlias("text")
    private String text;
}
