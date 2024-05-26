package project.vilsoncake.telegrambot.constant;

public class MessageConst {
    public static final String START_TEXT = """
            Hello, %s. Welcome to avgeek bot!
            I will send you all wide-body aircraft flying into your airport, as well as An-124 alerts.
                        
            For this, write the icao/iata code of your airport, you can get it on flightradar24.
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Great! How your airport is %s";
    public static final String CHANGE_AIRPORT_TEXT = "Write the icao/iata code of your airport, you can get it on flightradar24.";
}
