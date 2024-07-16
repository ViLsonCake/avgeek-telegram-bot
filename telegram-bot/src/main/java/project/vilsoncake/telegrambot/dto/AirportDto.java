package project.vilsoncake.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AirportDto {

    @JsonAlias("icao")
    private String icao;

    @JsonAlias("iata")
    private String iata;

    @JsonAlias("name")
    private String name;

    @JsonAlias("city")
    private String city;

    @JsonAlias("country")
    private String country;

    @JsonAlias("lat")
    private double latitude;

    @JsonAlias("lon")
    private double longitude;
}
