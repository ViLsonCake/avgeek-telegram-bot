package project.vilsoncake.telegrambot.constant;

public class MessageConst {
    public static final String START_TEXT = """
            Hello, %s. Welcome to avgeek bot!
            I will send you all wide-body aircraft flying into your airport, as well as An-124 alerts.
                        
            For this, write the icao/iata code of your airport, for example (waw) - Warsaw Chopin Airport.
            You can get it on flightradar24.
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Great! Now your airport is %s.";
    public static final String CHANGE_AIRPORT_TEXT = "Write the icao/iata code of your airport, you can get it on flightradar24.";
    public static final String CURRENT_AIRPORT_TEXT = "Your current airport is: %s (%s).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "The code \"%s\" is invalid, make sure you didn't make a typo.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "You have not yet selected an airport, please write its iata/icao code.";
    public static final String FLIGHT_TEXT = """
            Flight to your airport from %s.
            Aircraft: %s
            Airline: %s
            View there:
            https://www.flightradar24.com/airport/%s/arrivals
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            An-124 in the air right now.
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            View there:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT = """
            An-124 NEARLY CLOSE TO YOUR AIRPORT right now.
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            View there:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            An-124 IN YOUR AIRPORT RIGHT NOW.
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            View there:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_CODE = "A124";
}
