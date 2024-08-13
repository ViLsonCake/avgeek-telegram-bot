package project.vilsoncake.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class FlightDataDto {

    @JsonAlias("id")
    private String id;

    @JsonAlias("code")
    private String code;

    @JsonAlias("callsign")
    private String callsign;

    @JsonAlias("aircraft")
    private String aircraft;

    @JsonAlias("airline")
    private String airline;

    @JsonAlias("origin_airport_iata")
    private String originAirportIata;

    @JsonAlias("destination_airport_iata")
    private String destinationAirportIata;

    @JsonAlias("altitude")
    private int altitude;

    @JsonAlias("ground_speed")
    private int groundSpeed;

    @JsonAlias("vertical_speed")
    private int verticalSpeed;

    @JsonAlias("latitude")
    private double latitude;

    @JsonAlias("longitude")
    private double longitude;

    @JsonAlias("distance")
    private int distance;
}
