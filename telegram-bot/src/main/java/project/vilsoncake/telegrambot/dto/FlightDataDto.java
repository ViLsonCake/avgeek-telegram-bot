package project.vilsoncake.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class FlightDataDto {

    @JsonAlias("id")
    private String id;

    @JsonAlias("code")
    private String code;

    @JsonAlias("airline_name")
    private String airlineName;

    @JsonAlias("airport")
    private String airport;
}
