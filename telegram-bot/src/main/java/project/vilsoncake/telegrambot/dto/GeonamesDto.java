package project.vilsoncake.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class GeonamesDto {

    @JsonAlias("geonames")
    private List<GeonameDto> geonames;
}
