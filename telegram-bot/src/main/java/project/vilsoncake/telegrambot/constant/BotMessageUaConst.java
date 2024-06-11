package project.vilsoncake.telegrambot.constant;

public class BotMessageUaConst {
    public static final String START_TEXT = """
            Вітаю, %s. Ласкаво просимо до боту avgeek!
            Я буду відсилати вам всі широкофюзеляжні літаки, що прилітають у ваш аеропорт, а також сповіщення про Ан-124 які знаходяться зараз у небі.

            Для цього напишіть icao/iata код вашого аеропорту, наприклад (waw) - Warsaw Chopin Airport.
            Ви можете знайти його на flightradar24.
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Добре! Тепер ваш аеропорт це %s.";
    public static final String CHANGE_AIRPORT_TEXT = "Напишіть icao/iata код вашого аеропорту, ви можете знайти його на flightradar24.";
    public static final String CURRENT_AIRPORT_TEXT = "Ваш поточний аеропорт це %s (%s).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "Код \"%s\" некоректний, будь ласка перевірте чи не допустили ви помилку.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "Ви ще не обрали аеропорт, будь ласка введіть iata/icao код.";
    public static final String FLIGHT_TEXT = """
            Політ до вашого аеропорту з %s.
            Повітряне судно: %s
            Авіакомпанія: %s
            Дивіться тут:
            https://www.flightradar24.com/airport/%s/arrivals
            """;
    public static final String LIVE_FLIGHT_TEXT = """
            Активний політ до вашого аеропорту з %s.
            Повітряне судно: %s
            Авіакомпанія: %s
            Дивіться тут:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            Ан-124 знаходиться зараз у повітрі.
            Висота: %sм
            Швидкість: %sкм/г
            Відстань від вашого аеропорту: %sкм
            Дивіться тут:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT = """
            Ан-124 БЛИЗЬКО ДО ВАШОГО АЕРОПОРТУ ПРЯМО ЗАРАЗ.
            Висота: %sм
            Швидкість: %sкм/г
            Відстань від вашого аеропорту: %sкм
            Дивіться тут:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            Ан-124 ЗНАХОДИТЬСЯ У ВАШОМУ АЕРОПОРТУ ПРЯМО ЗАРАЗ.
            Висота: %sм
            Швидкість: %sкм/г
            Відстань від вашого аеропорту: %sкм
            Дивіться тут:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String CHOOSING_MODE_TEXT = "Виберіть режим:";
    public static final String CHOSEN_MODE_ALL_TEXT = "Ви будете отримувати сповіщення про польоти Ан-124 а також про польоти широкофюзеляжних літаках летящих у ваш аеропорт.";
    public static final String CHOSEN_MODE_ONLY_AN_124_TEXT = "Ви будете отримувати сповіщення ТІЛЬКИ про польоти Ан-124.";
    public static final String CHOSEN_MODE_ONLY_WIDE_BODY_TEXT = "Ви будете отримувати сповіщення ТІЛЬКИ про польоти широкофюзеляжних літаків у ваш аеропорт.";
    public static final String INCORRECT_MODE_TEXT = "\"%s\" це некоректний режим. Будь ласка виберіть з варіантів на клавіатурі.";
    public static final String WAIT_FOR_EMAIL_TEXT = "Введіть адресу email.";
    public static final String INVALID_EMAIL_TEXT = "Email який ви ввели некоректний.";
    public static final String INCORRECT_EMAIL_VERIFY_CODE = "Ви ввели неправильний код.";
    public static final String VERIFY_EMAIL_TEXT = "Добре! Тепер ваш email подтверждений і ви будете отримувати на нього сповіщення про Ан-124 в вашому аеропорту.";
    public static final String USER_NOT_ADDED_EMAIL_TEXT = "Ви поки що не добавили свою адресу email.";
    public static final String EMAIL_NOT_VERIFY_EMAIL = "Ваш email це %s, однак він не підтверджений.";
    public static final String MY_EMAIL_TEXT = "Ваш email це %s";
    public static final String REMOVE_EMAIL_TEXT = "Ваш email був видалений з боту.";
    public static final String CANCEL_ADDING_EMAIL_TRIGGER = "cancel";
    public static final String CANCEL_ADDING_EMAIL_TEXT = "Додавання email було відмінено.";
    public static final String WAIT_FOR_CODE_TEXT = """
            Код підтверждення був відправлений на ваш email, введіть його.
                        
            Якщо ви вказали неправильний email або хочете відмінити його додавання, введіть "cancel"
            """;
    public static final String SELECT_LANGUAGE_TEXT = "Виберіть мову:";
    public static final String LANGUAGE_SELECTED_TEXT = "Ваша мова %s.";
    public static final String INCORRECT_LANGUAGE_TEXT = "Введена мова некоректна. Виберіть мову в кнопок на клавіатурі.";
}
