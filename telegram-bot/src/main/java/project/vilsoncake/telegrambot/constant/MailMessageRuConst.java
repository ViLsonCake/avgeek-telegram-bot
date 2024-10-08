package project.vilsoncake.telegrambot.constant;

public class MailMessageRuConst {
    public static final String CODE_MESSAGE_SUBJECT = "Код подтверждения";
    public static final String CODE_MESSAGE_TEXT = "Ваш код подтверждения: %s";
    public static final String AN_124_IN_AIRPORT_MESSAGE_SUBJECT = "An-124 В ВАШЕМ АЭРОПОРТУ ПРЯМО СЕЙЧАС";
    public static final String AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT = """
            Авиакомпания %s
            Высота: %s
            Скорость: %s
            Расстояние от вашего аэропорта: %s
            Смотрите здесь:
            https://www.flightradar24.com/%s/%s
            """;
}
