package project.vilsoncake.telegrambot.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.UnitsSystem;

import static project.vilsoncake.telegrambot.constant.BotMessageEngConst.*;
import static project.vilsoncake.telegrambot.constant.BotMessageRuConst.*;
import static project.vilsoncake.telegrambot.constant.BotMessageUkConst.*;

@Component
public class UnitsUtils {

    public String convertSpeedToUserUnitsSystem(int value, UnitsSystem unitsSystem, BotLanguage botLanguage) {
        return switch (botLanguage) {
            case ENG -> getSpeedUnitEng(value, unitsSystem);
            case RU -> getSpeedUnitRu(value, unitsSystem);
            case UK -> getSpeedUnitUk(value, unitsSystem);
        };
    }

    public String convertDistanceToUserUnitsSystem(int value, UnitsSystem unitsSystem, BotLanguage botLanguage) {
        return switch (botLanguage) {
            case ENG -> getDistanceUnitEng(value, unitsSystem);
            case RU -> getDistanceUnitRu(value, unitsSystem);
            case UK -> getDistanceUnitUk(value, unitsSystem);
        };
    }

    public String convertAltitudeToUserUnitsSystem(int value, UnitsSystem unitsSystem, BotLanguage botLanguage) {
        return switch (botLanguage) {
            case ENG -> getAltitudeUnitEng(value, unitsSystem);
            case RU -> getAltitudeUnitRu(value, unitsSystem);
            case UK -> getAltitudeUnitUk(value, unitsSystem);
        };
    }

    public int convertMetricSpeedToMph(int value) {
        return (int) (value / 1.609344);
    }

    public int convertMetricSpeedToKnots(int value) {
        return (int) (value * 0.539957);
    }

    public int convertMetricDistanceToMiles(int value) {
        return (int) (value * 0.6213711922);
    }

    public int convertMetricDistanceToNauticalMiles(int value) {
        return (int) (value * 0.5399568035);
    }

    public int convertMetricAltitudeToFeet(int value) {
        return (int) (value * 3.28084);
    }

    private String getSpeedUnitEng(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_SPEED_UNIT_ENG;
            case IMPERIAL -> convertMetricSpeedToMph(value) + IMPERIAL_SPEED_UNIT_ENG;
            case AVIATION -> convertMetricSpeedToKnots(value) + AVIATION_SPEED_UNIT_ENG;
        };
    }

    private String getSpeedUnitRu(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_SPEED_UNIT_RU;
            case IMPERIAL -> convertMetricSpeedToMph(value) + IMPERIAL_SPEED_UNIT_RU;
            case AVIATION -> convertMetricSpeedToKnots(value) + AVIATION_SPEED_UNIT_RU;
        };
    }

    private String getSpeedUnitUk(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_SPEED_UNIT_UK;
            case IMPERIAL -> convertMetricSpeedToMph(value) + IMPERIAL_SPEED_UNIT_UK;
            case AVIATION -> convertMetricSpeedToKnots(value) + AVIATION_SPEED_UNIT_UK;
        };
    }

    private String getDistanceUnitEng(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_DISTANCE_UNIT_ENG;
            case IMPERIAL -> convertMetricDistanceToMiles(value) + IMPERIAL_DISTANCE_UNIT_ENG;
            case AVIATION -> convertMetricDistanceToNauticalMiles(value) + AVIATION_DISTANCE_UNIT_ENG;
        };
    }

    private String getDistanceUnitRu(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_DISTANCE_UNIT_RU;
            case IMPERIAL -> convertMetricDistanceToMiles(value) + IMPERIAL_DISTANCE_UNIT_RU;
            case AVIATION -> convertMetricDistanceToNauticalMiles(value) + AVIATION_DISTANCE_UNIT_RU;
        };
    }

    private String getDistanceUnitUk(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_DISTANCE_UNIT_UK;
            case IMPERIAL -> convertMetricDistanceToMiles(value) + IMPERIAL_DISTANCE_UNIT_UK;
            case AVIATION -> convertMetricDistanceToNauticalMiles(value) + AVIATION_DISTANCE_UNIT_UK;
        };
    }

    private String getAltitudeUnitEng(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_ALTITUDE_UNIT_ENG;
            case IMPERIAL -> convertMetricAltitudeToFeet(value) + IMPERIAL_ALTITUDE_UNIT_ENG;
            case AVIATION -> convertMetricAltitudeToFeet(value) + AVIATION_ALTITUDE_UNIT_ENG;
        };
    }

    private String getAltitudeUnitRu(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_ALTITUDE_UNIT_RU;
            case IMPERIAL -> convertMetricAltitudeToFeet(value) + IMPERIAL_ALTITUDE_UNIT_RU;
            case AVIATION -> convertMetricAltitudeToFeet(value) + AVIATION_ALTITUDE_UNIT_RU;
        };
    }

    private String getAltitudeUnitUk(int value, UnitsSystem unitsSystem) {
        return switch (unitsSystem) {
            case METRIC -> value + METRIC_ALTITUDE_UNIT_UK;
            case IMPERIAL -> convertMetricAltitudeToFeet(value) + IMPERIAL_ALTITUDE_UNIT_UK;
            case AVIATION -> convertMetricAltitudeToFeet(value) + AVIATION_ALTITUDE_UNIT_UK;
        };
    }
}
