package project.vilsoncake.telegrambot.constant;

import java.util.List;

import static project.vilsoncake.telegrambot.entity.enumerated.BotLanguage.*;

public class CommandNamesConst {
    public static final String START_COMMAND_NAME = "/start";
    public static final String CHANGE_AIRPORT_COMMAND_NAME = "/changeairport";
    public static final String CURRENT_AIRPORT_COMMAND_NAME = "/currentairport";
    public static final String PING_COMMAND_NAME = "/ping";
    public static final String CHANGE_MODE_COMMAND_NAME = "/changemode";
    public static final String CURRENT_MODE_COMMAND_NAME = "/currentmode";
    public static final String SET_EMAIL_COMMAND_NAME = "/setemail";
    public static final String MY_EMAIL_COMMAND_NAME = "/myemail";
    public static final String REMOVE_EMAIL_COMMAND_NAME = "/removeemail";
    public static final String CHANGE_LANGUAGE_COMMAND_NAME = "/lang";
    public static final String MODE_ALL_BUTTON_TEXT = "All";
    public static final String MODE_WIDE_BODY_BUTTON_TEXT = "Wide-body";
    public static final String MODE_AN_124_BUTTON_TEXT = "An-124";
    public static final String MODE_MUTE_TEXT = "Mute";
    public static final String MARKDOWN_PARSE_MODE = "Markdown";
    public static final List<String> MODES = List.of(MODE_ALL_BUTTON_TEXT, MODE_WIDE_BODY_BUTTON_TEXT, MODE_AN_124_BUTTON_TEXT, MODE_MUTE_TEXT);
    public static final List<String> LANGUAGES = List.of(ENG.name().toLowerCase(), RU.name().toLowerCase(), UK.name().toLowerCase());
    public static final String RU_LANGUAGE_CODE = "ru";
    public static final String UK_LANGUAGE_CODE = "uk";
}
