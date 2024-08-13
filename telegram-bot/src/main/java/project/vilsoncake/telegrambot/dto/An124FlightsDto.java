package project.vilsoncake.telegrambot.dto;

import lombok.Data;

import java.util.List;

@Data
public class An124FlightsDto {
    private List<FlightDataDto> flights;
}
