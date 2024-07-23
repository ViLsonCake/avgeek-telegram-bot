package project.vilsoncake.telegrambot.constant;

public class BotMessageRuConst {
    public static final String START_TEXT = """
            Здравствуйте, *%s*. Добро пожаловать в бот avgeek!
            Я буду присылать вам все широкофюзеляжные самолеты, прилетающие в ваш аэропорт, а также оповещения об Ан-124 которые находятся сейчас в небе.

            Для этого напишите _icao_/_iata_ код вашего аэропорта, например (*waw*) - Warsaw Chopin Airport.
            Вы можете получить его на [flightradar24](https://www.flightradar24.com).
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Отлично! теперь ваш аэропорт это *%s*.";
    public static final String CHANGE_AIRPORT_TEXT = "Напишите _icao_/_iata_ код вашего аэропорта, вы можете получить его на [flightradar24](https://www.flightradar24.com).";
    public static final String CURRENT_AIRPORT_TEXT = "Ваш текущий аэропорт это *%s* (*%s*).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "Код __%s__ *некорректен*, пожалуйста убедитесь что вы не допустили ошибку.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "Вы еще не выбрали аэропорт, пожалуйста напишите _iata_/_icao_ код.";
    public static final String FLIGHT_TEXT = """
            _Запланированный_ рейс (*%s*) в ваш аэропорт из *%s* (*%s*), *%s*.
            
            Воздушное судно: *%s*
            Авиакомпания: *%s*
            
            [Информация о воздушном судне](https://www.flightradar24.com/data/aircraft/%s)
            
            [Прибытия в ваш аэропорт](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String FLIGHT_WITHOUT_REGISTRATION_TEXT = """
            _Запланированный_ рейс в ваш аэропорт из *%s* (*%s*), *%s*.
            
            Воздушное судно: *%s*
            Авиакомпания: *%s*
            
            [Прибытия в ваш аэропорт](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String LIVE_FLIGHT_TEXT = """
            Рейс (*%s*) _вылетел_ в ваш аэропорт из *%s* (*%s*), *%s*.
            
            Воздушное судно: *%s*
            Авиакомпания: *%s*
            
            [Информация о воздушном судне](https://www.flightradar24.com/data/aircraft/%s)
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            *Ан-124* (*%s*) в воздухе прямо сейчас.
            
            Высота: *%sм*
            Скорость: *%sкм/ч*
            Расстояние от вашего аэропорта: *%sкм*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_TEXT = """
            *Ан-124* (*%s*) сейчас находится в *%s* (*%s*), *%s*, *%s*.
            
            Высота: %sм
            Скорость: %sкм/ч
            Расстояние от вашего аэропорта: %sкм
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_TEXT = """
            *Ан-124* (*%s*) _заходит на посадку_ в *%s* (*%s*), *%s*, *%s*.
            
            Высота: *%sм*
            Скорость: *%sкм/ч*
            Расстояние от вашего аэропорта: *%sкм*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_TAKEOFF_TEXT = """
            *Ан-124* (*%s*) _набирает высоту_ после взлета из *%s* (*%s*), *%s*, *%s*.
            
            Высота: *%sм*
            Скорость: *%sкм/ч*
            Расстояние от вашего аэропорта: *%sкм*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LIKELY_TO_LAND_AIRPORT_TEXT = """
            *Ан-124 (%s) ВЕРОЯТНО САДИТСЯ В ВАШ АЭРОПОРТ!*
            
            Высота: *%sм*
            Скорость: *%sкм/ч*
            Расстояние от вашего аэропорта: *%sкм*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT = """
            *Ан-124* (*%s*) _пролетает возле_ вашего аэропорта.
            
            Высота: *%sм*
            Скорость: *%sкм/ч*
            Расстояние от вашего аэропорта: *%sкм*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            *Ан-124 (%s) В ВАШЕМ АЭРОПОРТУ ПРЯМО СЕЙЧАС!*
            
            Высота: *%sм*
            Скорость: *%sкм/ч*
            Расстояние от вашего аэропорта: *%sкм*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String CHOOSING_MODE_TEXT = "Выберите _режим_";
    public static final String CHOSEN_MODE_ALL_TEXT = "Вы будете получать оповещения о полетах Ан-124, *а также* о полетах широкофюзеляжных самолетов в ваш аэропорт.";
    public static final String CHOSEN_MODE_ONLY_AN_124_TEXT = "Вы будете получать оповещения *только* о полетах Ан-124.";
    public static final String CHOSEN_MODE_ONLY_WIDE_BODY_TEXT = "Вы будете получать оповещения *только* о полетах широкофюзеляжных самолетов в ваш аэропорт.";
    public static final String INCORRECT_MODE_TEXT = "__%s__ это некорректный _режим_. Пожалуйста выберите из вариантов на клавиатуре.";
    public static final String WAIT_FOR_EMAIL_TEXT = "*Введите* адрес _email_";
    public static final String INVALID_EMAIL_TEXT = "_Email_ который вы ввели *некорректен*.";
    public static final String INCORRECT_EMAIL_VERIFY_CODE = "Вы ввели *неправильный* _код_.";
    public static final String VERIFY_EMAIL_TEXT = "Отлично! Теперь ваш _email_ подтвержден и вы будете получать на него оповещения об Ан-124 в вашем аэропорту.";
    public static final String USER_NOT_ADDED_EMAIL_TEXT = "Вы пока не добавили свой адрес _email_.";
    public static final String EMAIL_NOT_VERIFY_EMAIL = "Ваш _email_ это %s, однако он не подтвержден.";
    public static final String MY_EMAIL_TEXT = "Ваш _email_ это %s";
    public static final String REMOVE_EMAIL_TEXT = "Ваш _email_ был *удален* из бота.";
    public static final String CANCEL_ADDING_EMAIL_TRIGGER = "cancel";
    public static final String CANCEL_ADDING_EMAIL_TEXT = "Добавление _email_ было *отменено*.";
    public static final String WAIT_FOR_CODE_TEXT = """
            *Код подтверждения* был отправлен на ваш _email_, введите его
                        
            Если вы ввели неправильный _email_ или хотите *отменить* его добавление, напишите __cancel__
            """;
    public static final String SELECT_LANGUAGE_TEXT = "Выберите _язык_";
    public static final String LANGUAGE_SELECTED_TEXT = "Ваш _язык_ *%s*.";
    public static final String INCORRECT_LANGUAGE_TEXT = "Введенный _язык_ *некорректный*. Выберите язык из кнопок на клавиатуре.";
}
