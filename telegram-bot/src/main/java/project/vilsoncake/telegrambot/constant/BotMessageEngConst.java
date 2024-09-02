package project.vilsoncake.telegrambot.constant;

public class BotMessageEngConst {
    public static final String START_TEXT = """
            Hello dear user. Welcome to *Avgeek bot*!
                        
            *What can this bot do?*
            
            ✅ Send scheduled flights of *wide-body aircraft* to the airport of your choice so you can photograph them properly.
            
            ✅ Notify you about the flight of *An-124*, which in itself is quite rare, but also the bot will notify you separately if the An-124 decides to stop by your airport. And to make sure you don't miss it, you can add your email.
            
            ✅ You can change the mode of the bot, for example, to select notifications only about scheduled flights of wide-body airliners, or only about An-124s.
            
            ✅ The bot supports 3 languages: English, Ukrainian and Russian.
            
            Open the *command menu* for more details.
            
            
            *Now select the airport you're interested in*
            
            For this, write the *icao/iata* code of your airport.
            For example: *(FRA) - Frankfurt Airport*.
            
            You can get it on [Flightradar24](https://www.flightradar24.com).
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Great! Now your airport is *%s*.";
    public static final String CHANGE_AIRPORT_TEXT = "Write the *iata/icao* code of your airport, you can get it on [flightradar24](https://www.flightradar24.com).";
    public static final String CURRENT_AIRPORT_TEXT = "Your current airport is *%s* ([%s](https://www.flightradar24.com/airport/%s)).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "The code __%s__ is *invalid*, make sure you didn't make a typo.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "You have not yet selected an airport, please write its _iata_/_icao_ code.";
    public static final String FLIGHT_TEXT = """
            ⌛️ _Scheduled_ flight (*%s*) to your airport from *%s* (*%s*), *%s*.
            
            Aircraft: *%s*
            Airline: *%s*
            
            [Aircraft info](https://www.flightradar24.com/data/aircraft/%s)
            
            [Arrivals to your airport](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String FLIGHT_WITHOUT_REGISTRATION_TEXT = """
            ⌛️ _Scheduled_ flight to your airport from *%s* (*%s*), *%s*.
            
            Aircraft: *%s*
            Airline: *%s*
            
            [Arrivals to your airport](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String DEPARTED_FLIGHT_TEXT = """
            🛫 Flight (*%s*) _departed_ for your airport from *%s* (*%s*), *%s*.
            
            Aircraft: *%s*
            Airline: *%s*
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [Aircraft info](https://www.flightradar24.com/data/aircraft/%s)
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String LANDING_FLIGHT_TEXT = """
            🛬 Flight (*%s*) _approaches_ your airport from *%s* (*%s*), *%s*.
            
            Aircraft: *%s*
            Airline: *%s*
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [Aircraft info](https://www.flightradar24.com/data/aircraft/%s)
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            ✈️ *An-124* (*%s*) in the air right now.
            
            Altitude: *%s*:
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_BEFORE_FLIGHT_TEXT = """
            ✈️ *An-124* (*%s*) preparing for takeoff from *%s* (*%s*), *%s*, *%s*.
            
            Altitude: *%sm*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT = """
            ✈️ *An-124* (*%s*) landed at *%s* (*%s*), *%s*, *%s*.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT = """
            ✈️ *An-124* (*%s*) landed at *%s* (*%s*), *%s*, *%s*.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            Departure airport: *%s* (*%s*), *%s*, *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_UNKNOWN_AIRPORT_TEXT = """
            🛬 *An-124* (*%s*) _landing_ near *%s* (*%s*), *%s*, *%s*.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_KNOWN_AIRPORT_TEXT = """
            🛬 *An-124* (*%s*) _landing_ near *%s* (*%s*), *%s*, *%s*.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            Departure airport: *%s* (*%s*), *%s*, *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT = """
            🛬 *An-124* (*%s*) is _landing_.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT = """
            🛬 *An-124* (*%s*) is _landing_.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            Departure airport: *%s* (*%s*), *%s*, *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_TAKEOFF_NEAR_TEXT = """
            🛫 *An-124* (*%s*) _gaining altitude_ after takeoff near *%s* (*%s*), *%s*, *%s*.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_TAKEOFF_FROM_TEXT = """
            🛫 *An-124* (*%s*) _gaining altitude_ after takeoff from *%s* (*%s*), *%s*, *%s*.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LIKELY_TO_LAND_AIRPORT_TEXT = """
            ‼️ *An-124 (*%s*) WILL MOST LIKELY TO LAND AT YOUR AIRPORT* ‼️
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT = """
            ✈️ *An-124* (*%s*) _flying near_ your airport.
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            ‼️ *An-124 (*%s*) IN YOUR AIRPORT RIGHT NOW* ‼️
            
            Altitude: *%s*
            Ground speed: *%s*
            Distance from your airport: *%s*
            
            [View here](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_CODE = "A124";
    public static final String CHOOSING_MODE_TEXT = "Choose _mode_ please:";
    public static final String CHOSEN_MODE_ALL_TEXT = "You will receive notifications about An-124 flights *and* wide-body aircraft flights.";
    public static final String CHOSEN_MODE_ONLY_AN_124_TEXT = "You will receive notifications *only* about An-124 flights.";
    public static final String CHOSEN_MODE_ONLY_WIDE_BODY_TEXT = "You will receive notifications *only* for wide-body aircraft.";
    public static final String CHOSEN_MODE_MUTE_TEXT = "You will not receive *any* notifications.";
    public static final String INCORRECT_MODE_TEXT = "__%s__ is an incorrect mode. Select mode from the buttons on the keyboard.";
    public static final String WAIT_FOR_EMAIL_TEXT = "Enter your _email_.";
    public static final String INVALID_EMAIL_TEXT = "The _email_ you entered is *invalid*.";
    public static final String INCORRECT_EMAIL_VERIFY_CODE = "The _code_ is *incorrect*.";
    public static final String VERIFY_EMAIL_TEXT = "Great! Now your _email_ is *verified* and you'll receive alerts if An-124 is at your airport.";
    public static final String USER_NOT_ADDED_EMAIL_TEXT = "You haven't added an _email_ yet.";
    public static final String EMAIL_NOT_VERIFY_EMAIL = "Your _email_ is %s, *but it's not verified.*";
    public static final String MY_EMAIL_TEXT = "Your _email_ is %s";
    public static final String REMOVE_EMAIL_TEXT = "Your _email_ has been *removed* from bot.";
    public static final String CANCEL_TRIGGER = "cancel";
    public static final String CANCEL_ADDING_EMAIL_TEXT = "Adding _email_ has been *canceled*.";
    public static final String WAIT_FOR_CODE_TEXT = """
            A *confirmation code* has been sent to your _email_, *enter it*
                        
            If you have entered the wrong _email_ address or want to *cancel* adding _email_, please write __cancel__
            """;
    public static final String SELECT_LANGUAGE_TEXT = "Select _language_";
    public static final String LANGUAGE_SELECTED_TEXT = "Your _language_ is *%s*.";
    public static final String INCORRECT_LANGUAGE_TEXT = "Selected _language_ is *incorrect*. Select _language_ from the buttons on the keyboard.";
    public static final String CHANGE_UNITS_TEXT = """
            *Please select a system of units*
                        
            *Metric*
            _Altitude_ - meters
            _Distance_ - kilometers
            _Speed_ - kilometers per hour
                        
            *Imperial*
            _Altitude_ - feet
            _Distance_ - miles
            _Speed_ - miles per hour
                        
            *Aviation*
            _Altitude_ - feet
            _Distance_ - nautical miles
            _Speed_ - knots
            """;
    public static final String CHANGED_UNITS_TEXT = "Great! Now your System of Units is *%s*";
    public static final String CURRENT_METRIC_UNITS_TEXT = """
            *Your System of Units is Metric*
            
            _Altitude_ - meters
            _Distance_ - kilometers
            _Speed_ - kilometers per hour
            """;
    public static final String CURRENT_IMPERIAL_UNITS_TEXT = """
            *Your System of Units is Imperial*
            
            _Altitude_ - feet
            _Distance_ - miles
            _Speed_ - miles per hour
            """;
    public static final String CURRENT_AVIATION_UNITS_TEXT = """
            *Your System of Units is Aviation*
            
            _Altitude_ - feet
            _Distance_ - nautical miles
            _Speed_ - knots
            """;
    public static final String INCORRECT_UNITS_TEXT = "Selected _System of Units_ is *incorrect*. Select _System of Units_ from the buttons on the keyboard.";
    public static final String PING_COMMAND_TEXT = "pong";
    public static final String METRIC_SPEED_UNIT_ENG = "km/h";
    public static final String IMPERIAL_SPEED_UNIT_ENG = "mph";
    public static final String AVIATION_SPEED_UNIT_ENG = "kts";
    public static final String METRIC_DISTANCE_UNIT_ENG = "km";
    public static final String IMPERIAL_DISTANCE_UNIT_ENG = "mi";
    public static final String AVIATION_DISTANCE_UNIT_ENG = "nm";
    public static final String METRIC_ALTITUDE_UNIT_ENG = "m";
    public static final String IMPERIAL_ALTITUDE_UNIT_ENG = "ft";
    public static final String AVIATION_ALTITUDE_UNIT_ENG = "ft";
    public static final String FEEDBACK_COMMAND_TEXT = """
            *Write feedback here*
                        
            If you don't want to send feedback, write __"cancel"__
            """;
    public static final String FEEDBACK_SENT_TEXT = "Thank you for the feedback!";
    public static final String CANCEL_FEEDBACK_SEND_TEXT = "Sending feedback is canceled";
}
