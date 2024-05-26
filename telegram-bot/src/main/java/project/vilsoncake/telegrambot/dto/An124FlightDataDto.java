package project.vilsoncake.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class An124FlightDataDto {

    @JsonAlias("id")
    private String id;

    @JsonAlias("code")
    private String code;

    @JsonAlias("callsign")
    private String callsign;

    @JsonAlias("altitude")
    private int altitude;

    @JsonAlias("ground_speed")
    private int groundSpeed;

    @JsonAlias("latitude")
    private double latitude;

    @JsonAlias("longitude")
    private double longitude;

    @JsonAlias("distance")
    private String distance;
}
