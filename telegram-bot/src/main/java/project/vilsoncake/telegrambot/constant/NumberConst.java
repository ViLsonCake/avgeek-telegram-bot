package project.vilsoncake.telegrambot.constant;

public class NumberConst {
    public static final int SCHEDULED_FLIGHTS_CHECK_DELAY_IN_MINUTES = 15;
    public static final int LANDING_FLIGHTS_CHECK_DELAY_IN_MINUTES = 12;
    public static final int AN_124_FLIGHTS_CHECK_DELAY_IN_MINUTES = 1;
    public static final int FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM = 175;
    public static final int FLIGHT_IN_AIRPORT_DISTANCE_IN_KM = 3;
    public static final int FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M = 6000;
    public static final int ON_GROUND_ALTITUDE = 0;
    public static final int APPROACHING_VERTICAL_SPEED_IN_FPM = -500;
    public static final int LOW_ALTITUDE_IN_M = 5500;
    public static final int CLOSE_TO_AIRPORT_RANGE_IN_KM = 65;
    public static final int MAX_SAVED_FLIGHTS_COUNT = 100;
    public static final double SEMI_MAJOR_AXIS_MT = 6378137;
    public static final double SEMI_MINOR_AXIS_MT = 6356752.314245;
    public static final double FLATTENING = 1 / 298.257223563;
    public static final double ERROR_TOLERANCE = 1e-12;
    public static final int ON_GROUND_RADIUS = 7;
}
