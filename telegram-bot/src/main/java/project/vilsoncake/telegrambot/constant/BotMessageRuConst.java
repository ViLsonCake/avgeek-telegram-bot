package project.vilsoncake.telegrambot.constant;

public class BotMessageRuConst {
    public static final String START_TEXT = """
            Здравствуй дорогой пользователь. Добро пожаловать в бот *Avgeek*!
                        
            *Что умеет этот бот?*
            
            ✅ Отправлять запланированные рейсы *широкофюзеляжных авиалайнеров* в выбранный вами аэропорт для того чтобы вы смогли как следует их сфотографировать.
            
            ✅ Оповещать вас о полете *Ан-124*, что само по себе довольно редкое явление, но также бот отдельно уведомит вас в том случае если Ан-124 решит заглянуть к вам в аэропорт. А чтобы точно это не пропустить вы можете добавить свой email.
            
            ✅ Вы можете изменить режим работы бота, например выбрать только оповещения насчет запланированных рейсов широкофюзеляжных авиалайнеров, либо только оповещения об Ан-124.
            
            ✅ Бот поддерживает 3 языка на выбор: английский, украинский и русский.
            
            Откройте *меню команд* для более детального ознакомления.
            
            
            *Теперь выберите интересующий вас аэропорт*
            
            Для этого напишите *iata/icao* код вашего аэропорта.
            Например: *(FRA) - Frankfurt Airport*.
            
            Вы можете узнать его на [Flightradar24](https://www.flightradar24.com).
            """;
    public static final String CHOOSE_AIRPORT_TEXT = "Отлично! теперь ваш аэропорт это *%s*.";
    public static final String CHANGE_AIRPORT_TEXT = "Напишите *iata/icao* код вашего аэропорта, вы можете получить его на [flightradar24](https://www.flightradar24.com).";
    public static final String CURRENT_AIRPORT_TEXT = "Ваш текущий аэропорт это *%s* ([%s](https://www.flightradar24.com/airport/%s)).";
    public static final String INVALID_AIRPORT_CODE_TEXT = "Код __%s__ *некорректен*, пожалуйста убедитесь что вы не допустили ошибку.";
    public static final String USER_CANNOT_CHOOSE_AIRPORT_TEXT = "Вы еще не выбрали аэропорт, пожалуйста напишите _iata_/_icao_ код.";
    public static final String FLIGHT_TEXT = """
            ⌛️ _Запланированный_ рейс (*%s*) в ваш аэропорт из *%s* (*%s*), *%s*.
            
            Воздушное судно: *%s*
            Авиакомпания: *%s*
            
            [Информация о воздушном судне](https://www.flightradar24.com/data/aircraft/%s)
            
            [Прибытия в ваш аэропорт](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String FLIGHT_WITHOUT_REGISTRATION_TEXT = """
            ⌛️ _Запланированный_ рейс в ваш аэропорт из *%s* (*%s*), *%s*.
            
            Воздушное судно: *%s*
            Авиакомпания: *%s*
            
            [Прибытия в ваш аэропорт](https://www.flightradar24.com/airport/%s/arrivals)
            """;
    public static final String DEPARTED_FLIGHT_TEXT = """
            🛫 Рейс (*%s*) _вылетел_ в ваш аэропорт из *%s* (*%s*), *%s*.
            
            Воздушное судно: *%s*
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Информация о воздушном судне](https://www.flightradar24.com/data/aircraft/%s)
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String LANDING_FLIGHT_TEXT = """
            🛬 Рейс (*%s*) _заходит на посадку_ в ваш аэропорт из *%s* (*%s*), *%s*.
            
            Воздушное судно: *%s*
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Информация о воздушном судне](https://www.flightradar24.com/data/aircraft/%s)
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLIGHT_TEXT = """
            ✈️ *Ан-124* (*%s*) в воздухе прямо сейчас.
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_BEFORE_FLIGHT_TEXT = """
            ✈️ *Ан-124* (*%s*) готовится ко взлету из *%s* (*%s*), *%s*, *%s*.
            
            Авиакомпания: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT = """
            ✈️ *Ан-124* (*%s*) приземлился в *%s* (*%s*), *%s*, *%s*.
            
            Авиакомпания: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT = """
            ✈️ *Ан-124* (*%s*) приземлился в *%s* (*%s*), *%s*, *%s*.
            
            Авиакомпания: *%s*
            Расстояние от вашего аэропорта: *%s*
            Аэропорт вылета: *%s* (*%s*), *%s*, *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_UNKNOWN_AIRPORT_TEXT = """
            🛬 *Ан-124* (*%s*) _заходит на посадку_ вблизи *%s* (*%s*), *%s*, *%s*.
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_KNOWN_AIRPORT_TEXT = """
            🛬 *Ан-124* (*%s*) _заходит на посадку_ вблизи *%s* (*%s*), *%s*, *%s*.
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            Аэропорт вылета: *%s* (*%s*), *%s*, *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT = """
            🛬 *Ан-124* (*%s*) _заходит на посадку_.
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT = """
            🛬 *Ан-124* (*%s*) _заходит на посадку_.
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            Аэропорт вылета: *%s* (*%s*), *%s*, *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_TAKEOFF_NEAR_TEXT = """
            🛫 *Ан-124* (*%s*) _набирает высоту_ после взлета вблизи *%s* (*%s*), *%s*, *%s*.
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_TAKEOFF_FROM_TEXT = """
            🛫 *Ан-124* (*%s*) _набирает высоту_ после взлета из *%s* (*%s*), *%s*, *%s*.
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_LIKELY_TO_LAND_AIRPORT_TEXT = """
            ‼️ *Ан-124 (*%s*) ВЕРОЯТНО САДИТСЯ В ВАШ АЭРОПОРТ* ‼️
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT = """
            ✈️ *Ан-124* (*%s*) _пролетает возле_ вашего аэропорта.
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String AN_124_IN_YOUR_AIRPORT_NOW_TEXT = """
            ‼️ *Ан-124 (*%s*) В ВАШЕМ АЭРОПОРТУ ПРЯМО СЕЙЧАС* ‼️
            
            Авиакомпания: *%s*
            Высота: *%s*
            Скорость: *%s*
            Расстояние от вашего аэропорта: *%s*
            
            [Смотрите здесь](https://www.flightradar24.com/%s/%s)
            """;
    public static final String CHOOSING_MODE_TEXT = "Выберите _режим_";
    public static final String CHOSEN_MODE_ALL_TEXT = "Вы будете получать оповещения о полетах Ан-124, *а также* о полетах широкофюзеляжных самолетов в ваш аэропорт.";
    public static final String CHOSEN_MODE_ONLY_AN_124_TEXT = "Вы будете получать оповещения *только* о полетах Ан-124.";
    public static final String CHOSEN_MODE_ONLY_WIDE_BODY_TEXT = "Вы будете получать оповещения *только* о полетах широкофюзеляжных самолетов в ваш аэропорт.";
    public static final String CHOSEN_MODE_MUTE_TEXT = "Вы не будете получать *никаких* уведомлений.";
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
    public static final String CHANGE_UNITS_TEXT = """
            *Выберите систему единиц измерения*
            
            *Метрическая*
            _Высота_ - метры
            _Расстояние_ - километры
            _Скорость_ - километры в час
            
            *Имперская*
            _Высота_ - футы
            _Расстояние_ - мили
            _Скорость_ - мили в час
            
            *Авиационная*
            _Высота_ - футы
            _Расстояние_ - морские мили
            _Скорость_ - узлы
            """;
    public static final String CHANGED_UNITS_TEXT = "Отлично! Теперь ваша система единиц измерения это *%s*";
    public static final String CURRENT_METRIC_UNITS_TEXT = """
            *Ваша система единиц измерения это Метрическая*
            
            _Высота_ - метры
            _Расстояние_ - километры
            _Скорость_ - километры в час
            """;
    public static final String CURRENT_IMPERIAL_UNITS_TEXT = """
            *Ваша система единиц измерения это Имперская*
            
            _Высота_ - футы
            _Расстояние_ - мили
            _Скорость_ - мили в час
            """;
    public static final String CURRENT_AVIATION_UNITS_TEXT = """
            *Ваша система единиц измерения это Авиационная*
            
            _Высота_ - футы
            _Расстояние_ - морские мили
            _Скорость_ - узлы
            """;
    public static final String INCORRECT_UNITS_TEXT = "Введенная _система единиц измерения_ *некорректна*. Выберите систему единиц измерения из кнопок на клавиатуре.";
    public static final String METRIC_SPEED_UNIT_RU = "км/ч";
    public static final String IMPERIAL_SPEED_UNIT_RU = "миля/ч";
    public static final String AVIATION_SPEED_UNIT_RU = "уз";
    public static final String METRIC_DISTANCE_UNIT_RU = "км";
    public static final String IMPERIAL_DISTANCE_UNIT_RU = "миль";
    public static final String AVIATION_DISTANCE_UNIT_RU = "nm";
    public static final String METRIC_ALTITUDE_UNIT_RU = "м";
    public static final String IMPERIAL_ALTITUDE_UNIT_RU = "фут";
    public static final String AVIATION_ALTITUDE_UNIT_RU = "фут";
    public static final String FEEDBACK_COMMAND_TEXT = """
            *Напишите обратную связь сюда*
                        
            Если вы не хотите отправлять обратную связь, введите __"cancel"__
            """;
    public static final String FEEDBACK_SENT_TEXT = "Благодарю за обратную связь!";
    public static final String CANCEL_FEEDBACK_SEND_TEXT = "Отправление обратной связи отменено";
}
