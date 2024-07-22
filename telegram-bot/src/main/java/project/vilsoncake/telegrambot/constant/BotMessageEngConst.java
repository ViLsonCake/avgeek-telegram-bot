package project.vilsoncake.telegrambot.constant;

public class BotMessageEngConst {
    public static final String START_TEXT = """
            Hello, %s. Welcome to avgeek bot!
            I will send you all wide-body aircraft flying into your airport, as well as An-124 alerts.
                        
            For this, write the icao/iata code of your airport, for example (waw) - Warsaw Chopin Airport.
            You can get it on flightradar24.
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Great! Now your airport is %s.";
    public static final String CHANGE_AIRPORT_TEXT = "Write the icao/iata code of your airport, you can get it on flightradar24.";
    public static final String CURRENT_AIRPORT_TEXT = "Your current airport is %s (%s).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "The code \"%s\" is invalid, make sure you didn't make a typo.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "You have not yet selected an airport, please write its iata/icao code.";
    public static final String FLIGHT_TEXT = """
            Scheduled flight (%s) to your airport from %s (%s), %s.
            
            Aircraft: %s
            Airline: %s
            
            Aircraft info:
            https://www.flightradar24.com/data/aircraft/%s
            
            Arrivals to your airport:
            https://www.flightradar24.com/airport/%s/arrivals
            """;
    public static final String LIVE_FLIGHT_TEXT = """
            Flight (%s) departed for your airport from %s (%s), %s.
            
            Aircraft: %s
            Airline: %s
            
            Aircraft info:
            https://www.flightradar24.com/data/aircraft/%s
            
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            An-124 (%s) in the air right now.
            
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_ON_GROUND_TEXT = """
            An-124 (%s) is now at %s, %s, %s.
            
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_LANDING_TEXT = """
            An-124 (%s) approaches %s, %s, %s.
            
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_TAKEOFF_MESSAGE = """
            An-124 (%s) gaining altitude after takeoff from %s, %s, %s.
            
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_LIKELY_TO_LAND_AIRPORT_TEXT = """
            An-124 (%s) WILL MOST LIKELY TO LAND AT YOUR AIRPORT!
            
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT = """
            An-124 (%s) flying near your airport.
            
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            An-124 (%s) IN YOUR AIRPORT RIGHT NOW!
            
            Altitude: %sm:
            Ground speed: %skm/h
            Distance from your airport: %skm
            
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_CODE = "A124";
    public static final String CHOOSING_MODE_TEXT = "Choose mode please:";
    public static final String CHOSEN_MODE_ALL_TEXT = "You will receive alerts about An-124 flights and wide-body aircraft flights.";
    public static final String CHOSEN_MODE_ONLY_AN_124_TEXT = "You will receive alerts ONLY about An-124 flights.";
    public static final String CHOSEN_MODE_ONLY_WIDE_BODY_TEXT = "You will receive alerts ONLY for wide-body aircraft.";
    public static final String INCORRECT_MODE_TEXT = "\"%s\" is an incorrect mode. Select mode from the buttons on the keyboard.";
    public static final String WAIT_FOR_EMAIL_TEXT = "Enter your email.";
    public static final String INVALID_EMAIL_TEXT = "The email you entered is invalid.";
    public static final String INCORRECT_EMAIL_VERIFY_CODE = "The code is incorrect.";
    public static final String VERIFY_EMAIL_TEXT = "Great! Now your email is verified and you'll receive alerts if An-124 is at your airport.";
    public static final String USER_NOT_ADDED_EMAIL_TEXT = "You haven't added an email yet.";
    public static final String EMAIL_NOT_VERIFY_EMAIL = "Your email is %s, but it's not verified.";
    public static final String MY_EMAIL_TEXT = "Your email is %s";
    public static final String REMOVE_EMAIL_TEXT = "Your email has been removed from bot.";
    public static final String CANCEL_ADDING_EMAIL_TRIGGER = "cancel";
    public static final String CANCEL_ADDING_EMAIL_TEXT = "Adding email has been canceled.";
    public static final String WAIT_FOR_CODE_TEXT = """
            A confirmation code has been sent to your email, enter it.
                        
            If you have entered the wrong email address or want to cancel adding email, please write "cancel"
            """;
    public static final String SELECT_LANGUAGE_TEXT = "Select language:";
    public static final String LANGUAGE_SELECTED_TEXT = "Your language is %s.";
    public static final String INCORRECT_LANGUAGE_TEXT = "Selected language is incorrect. Select language from the buttons on the keyboard.";
}
