package project.vilsoncake.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class GeonameDto {

    @JsonAlias("name")
    private String name;

    @JsonAlias("countryName")
    private String countryName;

    @JsonAlias("lat")
    private double latitude;

    @JsonAlias("lng")
    private double longitude;
}
