package project.vilsoncake.telegrambot.constant;

public class MailMessageUaConst {
    public static final String CODE_MESSAGE_SUBJECT = "Код підтверждення";
    public static final String CODE_MESSAGE_TEXT = "Ваш код підтверждення: %s";
    public static final String AN_124_IN_AIRPORT_MESSAGE_SUBJECT = "An-124 В ВАШОМУ АЕРОПОРТУ ПРЯМО ЗАРАЗ";
    public static final String AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT = """
            Висота: %sm:
            Швидкість: %skm/h
            Відстань від вашого аеропорту: %skm
            Дивіться тут:
            https://www.flightradar24.com/%s/%s
            """;
}
