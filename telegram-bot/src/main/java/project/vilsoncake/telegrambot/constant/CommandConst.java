package project.vilsoncake.telegrambot.constant;

import java.util.List;

import static project.vilsoncake.telegrambot.entity.enumerated.BotLanguage.*;

public class CommandConst {
    public static final String START_COMMAND_NAME = "/start";
    public static final String START_COMMAND_DESCRIPTION = "Start the bot and select an airport";
    public static final String CHANGE_AIRPORT_COMMAND_NAME = "/changeairport";
    public static final String CHANGE_AIRPORT_COMMAND_DESCRIPTION = "Change your airport";
    public static final String CURRENT_AIRPORT_COMMAND_NAME = "/currentairport";
    public static final String CURRENT_AIRPORT_COMMAND_DESCRIPTION = "Get your current airport";
    public static final String PING_COMMAND_NAME = "/ping";
    public static final String PING_COMMAND_DESCRIPTION = "Check bot connection";
    public static final String CHANGE_MODE_COMMAND_NAME = "/changemode";
    public static final String CHANGE_MODE_COMMAND_DESCRIPTION = "Change bot mode: only an-124 flights or only wide-body aircraft flights, both by default.";
    public static final String CURRENT_MODE_COMMAND_NAME = "/currentmode";
    public static final String CURRENT_MODE_COMMAND_DESCRIPTION = "Get your current bot mode";
    public static final String SET_EMAIL_COMMAND_NAME = "/setemail";
    public static final String SET_EMAIL_COMMAND_DESCRIPTION = "Add email to send you mail if An-124 in your airport";
    public static final String MY_EMAIL_COMMAND_NAME = "/myemail";
    public static final String MY_EMAIL_COMMAND_DESCRIPTION = "Get your email";
    public static final String REMOVE_EMAIL_COMMAND_NAME = "/removeemail";
    public static final String REMOVE_EMAIL_COMMAND_DESCRIPTION = "Remove your email from the bot";
    public static final String CHANGE_LANGUAGE_COMMAND_NAME = "/lang";
    public static final String CHANGE_LANGUAGE_COMMAND_DESCRIPTION = "Change bot language";
    public static final String MODE_ALL_BUTTON_TEXT = "All";
    public static final String MODE_WIDE_BODY_BUTTON_TEXT = "Wide-body";
    public static final String MODE_AN_124_BUTTON_TEXT = "An-124";
    public static final String MARKDOWN_PARSE_MODE = "Markdown";
    public static final List<String> MODES = List.of(MODE_ALL_BUTTON_TEXT, MODE_WIDE_BODY_BUTTON_TEXT, MODE_AN_124_BUTTON_TEXT);
    public static final List<String> LANGUAGES = List.of(ENG.name().toLowerCase(), RU.name().toLowerCase(), UK.name().toLowerCase());
}
