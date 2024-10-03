package project.vilsoncake.telegrambot.constant;

public class BotMessageUkConst {
    public static final String START_TEXT = """
            Вітаю дорогий користувач. Ласкаво прошу до бота *Avgeek*!
                        
            *Що вміє цей бот?*
            
            ✅ Відправляти заплановані рейси *широкофюзеляжних авіалайнерів* до вибраного вами аеропорту для того щоб ви могли як слід їх сфотографувати.
            
            ✅ Сповіщати вас про політ *Ан-124*, що саме по собі досить рідкісне явище, але також бот окремо повідомить вас у тому разі, якщо Ан-124 вирішить зазирнути до вас в аеропорт. А щоб точно це не пропустити ви можете додати свій email.
            
            ✅ Ви можете змінити режим роботи бота, наприклад вибрати тільки сповіщення щодо запланованих рейсів широкофюзеляжних авіалайнерів, або тільки оповіщення про Ан-124.
            
            ✅ Бот підтримує 3 мови на вибір: англійську, українську та російську.
            
            Відкрийте *меню команд* для більш детального ознайомлення.
            
            
            *Тепер давайте оберемо аеропорт, який вас цікавить*.
            
            Для цього напишіть *iata/icao* код вашого аеропорту.
            Наприклад: *(FRA) - Frankfurt Airport*.
            Назву аеропорту і дужки писати не потрібно, тільки код.
            
            Ви можете дізнатися його на [Flightradar24](https://www.flightradar24.com).
            
            *Зверніть увагу!* Бот почне працювати тільки після вибору аеропорту.
            """;
    public static final String CHOOSE_AIRPORT_TEXT = """
            Чудово! Тепер ваш аеропорт це *%s*.
            
            Бот оновлюватиме інформацію про прибуття кожні 15 хвилин.
            """;
    public static final String CHANGE_AIRPORT_TEXT = "Напишіть *iata/icao* код вашого аеропорту, ви можете знайти його на [flightradar24](https://www.flightradar24.com).";
    public static final String CURRENT_AIRPORT_TEXT = "Ваш поточний аеропорт це *%s* ([%s](https://www.flightradar24.com/airport/%s)).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "Код __%s__ *некоректний*, будь ласка перевірте чи не допустили ви помилку.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "Ви ще не обрали аеропорт, будь ласка введіть _iata_/_icao_ код.";
    public static final String FLIGHT_TEXT = """
            ⌛️ _Запланований_ рейс (*%s*) до вашого аеропорту з *%s* (*%s*), *%s*.
            
            Повітряне судно: *%s*
            Авіакомпанія: *%s*
            
            ✈️ [Інформація про повітряне судно](https://www.flightradar24.com/data/aircraft/%s)
            
            🛬 [Прибуття до вашого аеропорту](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String FLIGHT_WITHOUT_REGISTRATION_TEXT = """
            ⌛️ _Запланований_ рейс до вашого аеропорту з *%s* (*%s*), *%s*.
            
            Повітряне судно: *%s*
            Авіакомпанія: *%s*
                        
            🛬 [Прибуття до вашого аеропорту](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String DEPARTED_FLIGHT_TEXT = """
            🛫 Рейс (*%s*) _вилетів_ до вашого аеропорту з *%s* (*%s*), *%s*.
            
            Повітряне судно: *%s*
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            ✈️ [Інформація про повітряне судно](https://www.flightradar24.com/data/aircraft/%s)
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String LANDING_FLIGHT_TEXT = """
            🛬 Рейс (*%s*) _заходить на посадку_ до вашого аеропорту з *%s* (*%s*), *%s*.
            
            Повітряне судно: *%s*
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            ✈️ [Інформація про повітряне судно](https://www.flightradar24.com/data/aircraft/%s)
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            ✈️ *Ан-124* (*%s*) знаходиться зараз у повітрі.
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_BEFORE_TEXT = """
            ✈️ *Ан-124* (*%s*) готується до зльоту з *%s* (*%s*), *%s*, *%s*.
            
            Авіакомпанія: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT = """
            ✈️ *Ан-124* (*%s*) приземлився в *%s* (*%s*), *%s*, *%s*.
            
            Авіакомпанія: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT = """
            ✈️ *Ан-124* (*%s*) приземлився в *%s* (*%s*), *%s*, *%s*.
            
            Авіакомпанія: *%s*
            Відстань від вашого аеропорту: *%s*
            Аеропорт вильоту: *%s* (*%s*), *%s*, *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_UNKNOWN_AIRPORT_TEXT = """
            🛬 *Ан-124* (*%s*) _заходить на посадку_ поблизу *%s* (*%s*), *%s*, *%s*.
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_KNOWN_AIRPORT_TEXT = """
            🛬 *Ан-124* (*%s*) _заходить на посадку_ поблизу *%s* (*%s*), *%s*, *%s*.
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            Аеропорт вильоту: *%s* (*%s*), *%s*, *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT = """
            🛬 *Ан-124* (*%s*) _заходить на посадку_.
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT = """
            🛬 *Ан-124* (*%s*) _заходить на посадку_.
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            Аеропорт вильоту: *%s* (*%s*), *%s*, *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_TAKEOFF_NEAR_TEXT = """
            🛫 *Ан-124* (*%s*) _набирає висоту_ після зльоту поблизу *%s* (*%s*), *%s*, *%s*.
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_TAKEOFF_FROM_TEXT = """
            🛫 *Ан-124* (*%s*) _набирає висоту_ після зльоту з *%s* (*%s*), *%s*, *%s*.
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LIKELY_TO_LAND_AIRPORT_TEXT = """
            ‼️ *Ан-124 (*%s*) СКОРІШ ЗА ВСЕ СІДАЄ В ВАШ АЕРОПОРТ* ‼️
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT = """
            ✈️ *Ан-124* (*%s*) _пролітає біля_ вашого аеропорту.
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            ‼️ *Ан-124 (*%s*) ЗНАХОДИТЬСЯ У ВАШОМУ АЕРОПОРТУ ПРЯМО ЗАРАЗ* ‼️
            
            Авіакомпанія: *%s*
            Висота: *%s*
            Швидкість: *%s*
            Відстань від вашого аеропорту: *%s*
            
            📍 [Дивіться тут](https://www.flightradar24.com/%s/%s)
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
    public static final String INCORRECT_LANGUAGE_TEXT = "Введена _мова_ *некоректна*. Виберіть мову з кнопок на клавіатурі.";
    public static final String CHANGE_UNITS_TEXT = """
            *Оберіть систему одиниць виміру*
            
            *Метрична*
            _Висота_ - метри
            _Відстань_ - кілометри
            _Швидкість_ - кілометри на годину
            
            *Імперська*
            _Висота_ - фути
            _Відстань_ - милі
            _Швидкість_ - милі на годину
            
            *Авіаційна*
            _Висота_ - фути
            _Відстань_ - морські милі
            _Швидкість_ - вузли
            """;
    public static final String CHANGED_UNITS_TEXT = "Добре! Тепер ваша система одиниць виміру це *%s*";
    public static final String CURRENT_METRIC_UNITS_TEXT = """
            *Ваша поточна система одиниць виміру це Метрична*
            
            _Висота_ - метри
            _Відстань_ - кілометри
            _Швидкість_ - кілометри на годину
            """;
    public static final String CURRENT_IMPERIAL_UNITS_TEXT = """
            *Ваша поточна система одиниць виміру це Імперська*
            
            _Висота_ - фути
            _Відстань_ - милі
            _Швидкість_ - милі на годину
            """;
    public static final String CURRENT_AVIATION_UNITS_TEXT = """
            *Ваша поточна система одиниць виміру це Авіаційна*
            
            _Висота_ - фути
            _Відстань_ - морські милі
            _Швидкість_ - вузли
            """;
    public static final String INCORRECT_UNITS_TEXT = "Введена _система одиниць виміру_ *некоректна*. Виберіть систему одиниць виміру з кнопок на клавіатурі.";
    public static final String METRIC_SPEED_UNIT_UK = "км/г";
    public static final String IMPERIAL_SPEED_UNIT_UK = "миля/г";
    public static final String AVIATION_SPEED_UNIT_UK = "вуз";
    public static final String METRIC_DISTANCE_UNIT_UK = "км";
    public static final String IMPERIAL_DISTANCE_UNIT_UK = "миль";
    public static final String AVIATION_DISTANCE_UNIT_UK = "nm";
    public static final String METRIC_ALTITUDE_UNIT_UK = "м";
    public static final String IMPERIAL_ALTITUDE_UNIT_UK = "фут";
    public static final String AVIATION_ALTITUDE_UNIT_UK = "фут";
    public static final String FEEDBACK_COMMAND_TEXT = """
            *Напишіть зворотній зв'язок сюди*
            
            Якщо ви не хочете надсилати зворотній зв'язок, введіть __"cancel"__
            """;
    public static final String FEEDBACK_SENT_TEXT = "Дякую за зворотній зв'язок!";
    public static final String CANCEL_FEEDBACK_SEND_TEXT = "Надсилання зворотнього зв'язку скасовано";
}
