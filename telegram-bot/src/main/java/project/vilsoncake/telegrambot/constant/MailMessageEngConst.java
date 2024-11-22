package project.vilsoncake.telegrambot.constant;

public class MailMessageEngConst {
    public static final String CODE_MESSAGE_SUBJECT = "Verify Code";
    public static final String CODE_MESSAGE_TEXT = "Your verify code is: %s";
    public static final String AN_124_IN_AIRPORT_MESSAGE_SUBJECT = "‼️ An-124 In Your Airport ‼️";
    public static final String AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT = """
            Airline: %s
            Altitude: %s
            Ground speed: %s
            Distance from your airport: %s
            View here:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_LIKELY_IN_AIRPORT_MESSAGE_SUBJECT = "‼️ An-124 Likely to land at Your Airport ‼️";
    public static final String AN_124_LIKELY_IN_YOUR_AIRPORT_MESSAGE_TEXT = """
            Airline: %s
            Altitude: %s
            Ground speed: %s
            Distance from your airport: %s
            View here:
            https://www.flightradar24.com/%s/%s
            """;
}
