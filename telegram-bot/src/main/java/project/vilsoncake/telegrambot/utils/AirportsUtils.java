package project.vilsoncake.telegrambot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.vilsoncake.telegrambot.dto.AirportCodesDto;
import project.vilsoncake.telegrambot.dto.AirportDto;
import project.vilsoncake.telegrambot.exception.AirportNotFoundException;

import java.util.List;

import static project.vilsoncake.telegrambot.constant.NumberConst.*;

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
        double U1 = Math.atan((1 - FLATTENING) * Math.tan(Math.toRadians(latitude1)));
        double U2 = Math.atan((1 - FLATTENING) * Math.tan(Math.toRadians(latitude2)));

        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);

        double longitudeDifference = Math.toRadians(longitude2 - longitude1);
        double previousLongitudeDifference;

        double sinSigma, cosSigma, sigma, sinAlpha, cosSqAlpha, cos2SigmaM;

        do {
            sinSigma = Math.sqrt(Math.pow(cosU2 * Math.sin(longitudeDifference), 2) +
                    Math.pow(cosU1 * sinU2 - sinU1 * cosU2 * Math.cos(longitudeDifference), 2));
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * Math.cos(longitudeDifference);
            sigma = Math.atan2(sinSigma, cosSigma);
            sinAlpha = cosU1 * cosU2 * Math.sin(longitudeDifference) / sinSigma;
            cosSqAlpha = 1 - Math.pow(sinAlpha, 2);
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            if (Double.isNaN(cos2SigmaM)) {
                cos2SigmaM = 0;
            }
            previousLongitudeDifference = longitudeDifference;
            double C = FLATTENING / 16 * cosSqAlpha * (4 + FLATTENING * (4 - 3 * cosSqAlpha));
            longitudeDifference = Math.toRadians(longitude2 - longitude1) + (1 - C) * FLATTENING * sinAlpha *
                    (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * Math.pow(cos2SigmaM, 2))));
        } while (Math.abs(longitudeDifference - previousLongitudeDifference) > ERROR_TOLERANCE);

        double uSq = cosSqAlpha * (Math.pow(SEMI_MAJOR_AXIS_MT, 2) - Math.pow(SEMI_MINOR_AXIS_MT, 2)) / Math.pow(SEMI_MINOR_AXIS_MT, 2);

        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));

        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * Math.pow(cos2SigmaM, 2))
                - B / 6 * cos2SigmaM * (-3 + 4 * Math.pow(sinSigma, 2)) * (-3 + 4 * Math.pow(cos2SigmaM, 2))));

        double distanceMt = SEMI_MINOR_AXIS_MT * A * (sigma - deltaSigma);
        return (int) (distanceMt / 1000);
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
