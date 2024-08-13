package project.vilsoncake.telegrambot.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlightsDto {
    private List<ScheduledFlightDataDto> flights;
}
