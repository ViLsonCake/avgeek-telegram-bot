package project.vilsoncake.telegrambot.constant;

public class MessageConst {
    public static final String START_TEXT = """
            Hello, %s. Welcome to avgeek bot!
            I will send you all wide-body aircraft flying into your airport, as well as An-124 alerts.
                        
            For this, write the icao/iata code of your airport, you can get it on flightradar24.
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Great! How your airport is %s.";
    public static final String CHANGE_AIRPORT_TEXT = "Write the icao/iata code of your airport, you can get it on flightradar24.";
    public static final String INVALID_AIRPORT_CODE_TEXT = "The code \"%s\" is invalid, make sure you didn't make a typo.";
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
            Distance from your airport: %s
            View there:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_CODE = "A124";
}
