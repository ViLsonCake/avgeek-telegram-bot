package project.vilsoncake.telegrambot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.vilsoncake.telegrambot.dto.AirportCodesDto;
import project.vilsoncake.telegrambot.dto.AirportDto;
import project.vilsoncake.telegrambot.exception.AirportNotFoundException;

import java.util.List;

import static project.vilsoncake.telegrambot.constant.NumberConst.EARTH_RADIUS;

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

    public AirportDto findClosestAirportByCoordinates(double latitude, double longitude, int radius) {
        for (AirportDto airport : airports) {
            int distance = calculateDistanceByCoordinates(latitude, longitude, airport.getLatitude(), airport.getLongitude());

            if (distance <= radius) {
                return airport;
            }
        }

        throw new AirportNotFoundException(String.format("No airport within %s km radius found", radius));
    }

    public int calculateDistanceByCoordinates(double latitude1, double longitude1, double latitude2, double longitude2) {
        double lat1Rad = Math.toRadians(latitude1);
        double lat2Rad = Math.toRadians(latitude2);
        double lon1Rad = Math.toRadians(longitude1);
        double lon2Rad = Math.toRadians(longitude2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        return (int) (Math.sqrt(x * x + y * y) * EARTH_RADIUS);
    }

    public AirportDto getAirportByIataCode(String iataCode) {
        for (AirportDto airport : airports) {
            if (airport.getIata().equalsIgnoreCase(iataCode)) {
                return airport;
            }
        }

        throw new AirportNotFoundException(String.format("Airport with iata code %s not found", iataCode));
    }
}
