package project.vilsoncake.telegrambot.constant;

public class BotMessageUkConst {
    public static final String START_TEXT = """
            Вітаю, *%s*. Ласкаво просимо до боту avgeek!
            Я буду відсилати вам всі широкофюзеляжні літаки, що прилітають у ваш аеропорт, а також сповіщення про Ан-124 які знаходяться зараз у небі.

            Для цього напишіть _icao_/_iata_ код вашого аеропорту, наприклад (*waw*) - Warsaw Chopin Airport.
            Ви можете знайти його на [flightradar24](https://www.flightradar24.com).
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Добре! Тепер ваш аеропорт це *%s*.";
    public static final String CHANGE_AIRPORT_TEXT = "Напишіть _icao_/_iata_ код вашого аеропорту, ви можете знайти його на [flightradar24](https://www.flightradar24.com).";
    public static final String CURRENT_AIRPORT_TEXT = "Ваш поточний аеропорт це *%s* ([%s](https://www.flightradar24.com/airport/%s)).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "Код __%s__ *некоректний*, будь ласка перевірте чи не допустили ви помилку.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "Ви ще не обрали аеропорт, будь ласка введіть _iata_/_icao_ код.";
    public static final String FLIGHT_TEXT = """
            _Запланований_ рейс (*%s*) до вашого аеропорту з *%s* (*%s*), *%s*.
            
            Повітряне судно: *%s*
            Авіакомпанія: *%s*
            
            [Інформація про повітряне судно](https://www.flightradar24.com/data/aircraft/%s)
            
            [Прибуття до вашого аеропорту](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String FLIGHT_WITHOUT_REGISTRATION_TEXT = """
            _Запланований_ рейс до вашого аеропорту з *%s* (*%s*), *%s*.
            
            Повітряне судно: *%s*
            Авіакомпанія: *%s*
                        
            [Прибуття до вашого аеропорту](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String LIVE_FLIGHT_TEXT = """
            Рейс (*%s*) _вилетів_ до вашого аеропорту з *%s* (*%s*), *%s*.
            
            Повітряне судно: *%s*
            Авіакомпанія: *%s*
            
            [Інформація про повітряне судно](https://www.flightradar24.com/data/aircraft/%s)
            
            [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            *Ан-124* (*%s*) знаходиться зараз у повітрі.
            
            Висота: *%sм*
            Швидкість: *%sкм/г*
            Відстань від вашого аеропорту: *%sкм*
            
            [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_TEXT = """
            *Ан-124* (*%s*) зараз знаходиться в *%s* (*%s*), *%s*, *%s*.
            
            Висота: *%sм*
            Швидкість: *%sкм/г*
            Відстань від вашого аеропорту: *%sкм*
            
            [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_TEXT = """
            *Ан-124* (*%s*) _заходить на посадку_ поблизу *%s* (*%s*), *%s*, *%s*.
            
            Висота: *%sм*
            Швидкість: *%sкм/г*
            Відстань від вашого аеропорту: *%sкм*
            
            [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_TAKEOFF_TEXT = """
            *Ан-124* (*%s*) _набирає висоту_ після зльоту поблизу *%s* (*%s*), *%s*, *%s*.
            
            Висота: *%sм*
            Швидкість: *%sкм/г*
            Відстань від вашого аеропорту: *%sкм*
            
            [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LIKELY_TO_LAND_AIRPORT_TEXT = """
            *Ан-124 (%s) СКОРІШ ЗА ВСЕ СІДАЄ В ВАШ АЕРОПОРТ!*
            
            Висота: *%sм*
            Швидкість: *%sкм/г*
            Відстань від вашого аеропорту: *%sкм*
            
            [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT = """
            *Ан-124* (*%s*) _пролітає біля_ вашого аеропорту.
            
            Висота: *%sм*
            Швидкість: *%sкм/г*
            Відстань від вашого аеропорту: *%sкм*
            
            [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            *Ан-124 (%s) ЗНАХОДИТЬСЯ У ВАШОМУ АЕРОПОРТУ ПРЯМО ЗАРАЗ!*
            
            Висота: *%sм*
            Швидкість: *%sкм/г*
            Відстань від вашого аеропорту: *%sкм*
            
            [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String CHOOSING_MODE_TEXT = "Виберіть _режим_";
    public static final String CHOSEN_MODE_ALL_TEXT = "Ви будете отримувати сповіщення про польоти Ан-124 *а також* про польоти широкофюзеляжних літаках летящих у ваш аеропорт.";
    public static final String CHOSEN_MODE_ONLY_AN_124_TEXT = "Ви будете отримувати сповіщення *тільки* про польоти Ан-124.";
    public static final String CHOSEN_MODE_ONLY_WIDE_BODY_TEXT = "Ви будете отримувати сповіщення *тільки* про польоти широкофюзеляжних літаків у ваш аеропорт.";
    public static final String CHOSEN_MODE_MUTE_TEXT = "Ви не будете отримувати *ніяких* сповіщень.";
    public static final String INCORRECT_MODE_TEXT = "__%s__ це некоректний режим. Будь ласка виберіть з варіантів на клавіатурі.";
    public static final String WAIT_FOR_EMAIL_TEXT = "Введіть адресу _email_";
    public static final String INVALID_EMAIL_TEXT = "_Email_ який ви ввели *некоректний*.";
    public static final String INCORRECT_EMAIL_VERIFY_CODE = "Ви ввели *неправильний* _код_.";
    public static final String VERIFY_EMAIL_TEXT = "Добре! Тепер ваш _email_ подтверждений і ви будете отримувати на нього сповіщення про Ан-124 в вашому аеропорту.";
    public static final String USER_NOT_ADDED_EMAIL_TEXT = "Ви поки що не добавили свою адресу _email_.";
    public static final String EMAIL_NOT_VERIFY_EMAIL = "Ваш _email_ це %s, однак він не підтверджений.";
    public static final String MY_EMAIL_TEXT = "Ваш _email_ це %s";
    public static final String REMOVE_EMAIL_TEXT = "Ваш _email_ був *видалений* з боту.";
    public static final String CANCEL_ADDING_EMAIL_TRIGGER = "cancel";
    public static final String CANCEL_ADDING_EMAIL_TEXT = "Додавання _email_ було *відмінено*.";
    public static final String WAIT_FOR_CODE_TEXT = """
            *Код підтверждення* був відправлений на ваш _email_, введіть його
                        
            Якщо ви вказали неправильний _email_ або хочете *відмінити* його додавання, введіть __cancel__
            """;
    public static final String SELECT_LANGUAGE_TEXT = "Виберіть _мову_";
    public static final String LANGUAGE_SELECTED_TEXT = "Ваша _мова_ *%s*.";
    public static final String INCORRECT_LANGUAGE_TEXT = "Введена _мова_ *некоректна*. Виберіть мову в кнопок на клавіатурі.";
}
