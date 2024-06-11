package project.vilsoncake.telegrambot.constant;

public class BotMessageRuConst {
    public static final String START_TEXT = """
            Здравствуйте, %s. Добро пожаловать в бот avgeek!
            Я буду присылать вам все широкофюзеляжные самолеты, прилетающие в ваш аэропорт, а также оповещения об Ан-124 которые находятся сейчас в небе.

            Для этого напишите icao/iata код вашего аэропорта, например (waw) - Warsaw Chopin Airport.
            Вы можете получить его на flightradar24.
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Отлично! теперь ваш аэропорт это %s.";
    public static final String CHANGE_AIRPORT_TEXT = "Напишите icao/iata код вашего аэропорта, вы можете получить его на flightradar24.";
    public static final String CURRENT_AIRPORT_TEXT = "Ваш текущий аэропорт это %s (%s).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "Код \"%s\" некорректен, пожалуйста убедитесь что вы не допустили ошибку.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "Вы еще не выбрали аэропорт, пожалуйста напишите iata/icao код.";
    public static final String FLIGHT_TEXT = """
            Полет в ваш аэропорт из %s.
            Воздушное судно: %s
            Авиакомпания: %s
            Смотрите здесь:
            https://www.flightradar24.com/airport/%s/arrivals
            """;
    public static final String LIVE_FLIGHT_TEXT = """
            Активный полет в ваш аэропорт из %s.
            Воздушное судно: %s
            Авиакомпания: %s
            Смотрите здесь:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            Ан-124 в воздухе прямо сейчас.
            Высота: %sм
            Скорость: %sкм/ч
            Расстояние от вашего аэропорта: %sкм
            Смотрите здесь:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT = """
            Ан-124 БЛИЗКО К ВАШЕМУ АЭРОПОРТУ прямо сейчас.
            Высота: %sм
            Скорость: %sкм/ч
            Расстояние от вашего аэропорта: %sкм
            Смотрите здесь:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            Ан-124 В ВАШЕМ АЭРОПОРТУ ПРЯМО СЕЙЧАС.
            Высота: %sм
            Скорость: %sкм/ч
            Расстояние от вашего аэропорта: %sкм
            Смотрите здесь:
            https://www.flightradar24.com/%s/%s
            """;
    public static final String CHOOSING_MODE_TEXT = "Выберите режим:";
    public static final String CHOSEN_MODE_ALL_TEXT = "Вы будете получать оповещения о полетах Ан-124, а также о полетах широкофюзеляжных самолетов в ваш аэропорт.";
    public static final String CHOSEN_MODE_ONLY_AN_124_TEXT = "Вы будете получать оповещения ТОЛЬКО о полетах Ан-124.";
    public static final String CHOSEN_MODE_ONLY_WIDE_BODY_TEXT = "Вы будете получать оповещения ТОЛЬКО о полетах широкофюзеляжных самолетов в ваш аэропорт.";
    public static final String INCORRECT_MODE_TEXT = "\"%s\" это некорректный режим. Пожалуйста выберите из вариантов на клавиатуре.";
    public static final String WAIT_FOR_EMAIL_TEXT = "Введите адрес email.";
    public static final String INVALID_EMAIL_TEXT = "Email который вы ввели некорректен.";
    public static final String INCORRECT_EMAIL_VERIFY_CODE = "Вы ввели неправильный код.";
    public static final String VERIFY_EMAIL_TEXT = "Отлично! Теперь ваш email подтвержден и вы будете получать на него оповещения об Ан-124 в вашем аэропорту.";
    public static final String USER_NOT_ADDED_EMAIL_TEXT = "Вы пока не добавили свой адрес email.";
    public static final String EMAIL_NOT_VERIFY_EMAIL = "Ваш email это %s, однако он не подтвержден.";
    public static final String MY_EMAIL_TEXT = "Ваш email это %s";
    public static final String REMOVE_EMAIL_TEXT = "Ваш email был удален из бота.";
    public static final String CANCEL_ADDING_EMAIL_TRIGGER = "cancel";
    public static final String CANCEL_ADDING_EMAIL_TEXT = "Adding email has been canceled.";
    public static final String WAIT_FOR_CODE_TEXT = """
            Код подтверждения был отправлен на ваш email, введите его.
                        
            Если вы ввели неправильный email или хотите отменить его добавление, напишите "cancel"
            """;
    public static final String SELECT_LANGUAGE_TEXT = "Выберите язык:";
    public static final String LANGUAGE_SELECTED_TEXT = "Ваш язык %s.";
    public static final String INCORRECT_LANGUAGE_TEXT = "Введенный язык некорректный. Выберите язык из кнопок на клавиатуре.";
}
