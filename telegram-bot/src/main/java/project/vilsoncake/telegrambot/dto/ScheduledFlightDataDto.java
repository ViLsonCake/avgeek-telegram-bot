package project.vilsoncake.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ScheduledFlightDataDto {

    @JsonAlias("id")
    private String id;

    @JsonAlias("code")
    private String code;

    @JsonAlias("aircraft")
    private String aircraft;

    @JsonAlias("airline_name")
    private String airlineName;

    @JsonAlias("airport")
    private String airport;

    @JsonAlias("iata")
    private String iata;

    @JsonAlias("icao")
    private String icao;

    @JsonAlias("callsign")
    private String callsign;

    @JsonAlias("registration")
    private String registration;

    @JsonAlias("live")
    private boolean live;

    @JsonAlias("text")
    private String text;
}
