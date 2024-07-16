package project.vilsoncake.telegrambot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.vilsoncake.telegrambot.dto.AirportCodesDto;
import project.vilsoncake.telegrambot.dto.AirportDto;
import project.vilsoncake.telegrambot.exception.AirportNotFoundException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AirportsUtils {

    private final List<AirportDto> airports;

    public AirportCodesDto validateAirportCode(String code) {
        for (AirportDto airport : airports) {
            if (airport.getIata().equalsIgnoreCase(code) || airport.getIcao().equalsIgnoreCase(code)) {
                return new AirportCodesDto(airport.getIata(), airport.getIcao());
            }
        }

        throw new AirportNotFoundException(String.format("Airport with code %s not found", code));
    }
}
