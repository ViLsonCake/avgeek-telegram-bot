package project.vilsoncake.telegrambot.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.telegrambot.constant.BotMessageEngConst;
import project.vilsoncake.telegrambot.constant.BotMessageRuConst;
import project.vilsoncake.telegrambot.constant.BotMessageUaConst;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.BotMessageTemplate;

@Component
public class BotMessageUtils {

    public String getMessageByLanguage(BotMessageTemplate template, BotLanguage language) {
        return switch (language) {
            case ENG -> getEngConstant(template);
            case RU -> getRuConstant(template);
            case UA -> getUaConstant(template);
        };
    }

    private String getEngConstant(BotMessageTemplate template) {
        return switch (template) {
            case START_TEXT -> BotMessageEngConst.START_TEXT;
            case CHOOSE_AIRPORT_TEXT -> BotMessageEngConst.CHOOSE_AIRPORT_TEXT;
            case CHANGE_AIRPORT_TEXT -> BotMessageEngConst.CHANGE_AIRPORT_TEXT;
            case CURRENT_AIRPORT_TEXT -> BotMessageEngConst.CURRENT_AIRPORT_TEXT;
            case INVALID_AIRPORT_CODE_TEXT -> BotMessageEngConst.INVALID_AIRPORT_CODE_TEXT;
            case USER_CANNOT_CHOOSE_AIRPORT_TEXT -> BotMessageEngConst.USER_CANNOT_CHOOSE_AIRPORT_TEXT;
            case FLIGHT_TEXT -> BotMessageEngConst.FLIGHT_TEXT;
            case LIVE_FLIGHT_TEXT -> BotMessageEngConst.LIVE_FLIGHT_TEXT;
            case AN_124_FLIGHT_TEXT -> BotMessageEngConst.AN_124_FLIGHT_TEXT;
            case AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT -> BotMessageEngConst.AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT;
            case AN_124_IN_YOUR_AIRPORT_NOW_TEXT -> BotMessageEngConst.AN_124_IN_YOUR_AIRPORT_NOW_TEXT;
            case CHOOSING_MODE_TEXT -> BotMessageEngConst.CHOOSING_MODE_TEXT;
            case CHOSEN_MODE_ALL_TEXT -> BotMessageEngConst.CHOSEN_MODE_ALL_TEXT;
            case CHOSEN_MODE_ONLY_AN_124_TEXT -> BotMessageEngConst.CHOSEN_MODE_ONLY_AN_124_TEXT;
            case CHOSEN_MODE_ONLY_WIDE_BODY_TEXT -> BotMessageEngConst.CHOSEN_MODE_ONLY_WIDE_BODY_TEXT;
            case INCORRECT_MODE_TEXT -> BotMessageEngConst.INCORRECT_MODE_TEXT;
            case WAIT_FOR_EMAIL_TEXT -> BotMessageEngConst.WAIT_FOR_EMAIL_TEXT;
            case INVALID_EMAIL_TEXT -> BotMessageEngConst.INVALID_EMAIL_TEXT;
            case INCORRECT_EMAIL_VERIFY_CODE -> BotMessageEngConst.INCORRECT_EMAIL_VERIFY_CODE;
            case VERIFY_EMAIL_TEXT -> BotMessageEngConst.VERIFY_EMAIL_TEXT;
            case USER_NOT_ADDED_EMAIL_TEXT -> BotMessageEngConst.USER_NOT_ADDED_EMAIL_TEXT;
            case EMAIL_NOT_VERIFY_EMAIL -> BotMessageEngConst.EMAIL_NOT_VERIFY_EMAIL;
            case MY_EMAIL_TEXT -> BotMessageEngConst.MY_EMAIL_TEXT;
            case REMOVE_EMAIL_TEXT -> BotMessageEngConst.REMOVE_EMAIL_TEXT;
            case CANCEL_ADDING_EMAIL_TRIGGER -> BotMessageEngConst.CANCEL_ADDING_EMAIL_TRIGGER;
            case CANCEL_ADDING_EMAIL_TEXT -> BotMessageEngConst.CANCEL_ADDING_EMAIL_TEXT;
            case WAIT_FOR_CODE_TEXT -> BotMessageEngConst.WAIT_FOR_CODE_TEXT;
            case SELECT_LANGUAGE_TEXT -> BotMessageEngConst.SELECT_LANGUAGE_TEXT;
            case LANGUAGE_SELECTED_TEXT -> BotMessageEngConst.LANGUAGE_SELECTED_TEXT;
            case INCORRECT_LANGUAGE_TEXT -> BotMessageEngConst.INCORRECT_LANGUAGE_TEXT;
        };
    }

    private String getRuConstant(BotMessageTemplate template) {
        return switch (template) {
            case START_TEXT -> BotMessageRuConst.START_TEXT;
            case CHOOSE_AIRPORT_TEXT -> BotMessageRuConst.CHOOSE_AIRPORT_TEXT;
            case CHANGE_AIRPORT_TEXT -> BotMessageRuConst.CHANGE_AIRPORT_TEXT;
            case CURRENT_AIRPORT_TEXT -> BotMessageRuConst.CURRENT_AIRPORT_TEXT;
            case INVALID_AIRPORT_CODE_TEXT -> BotMessageRuConst.INVALID_AIRPORT_CODE_TEXT;
            case USER_CANNOT_CHOOSE_AIRPORT_TEXT -> BotMessageRuConst.USER_CANNOT_CHOOSE_AIRPORT_TEXT;
            case FLIGHT_TEXT -> BotMessageRuConst.FLIGHT_TEXT;
            case LIVE_FLIGHT_TEXT -> BotMessageRuConst.LIVE_FLIGHT_TEXT;
            case AN_124_FLIGHT_TEXT -> BotMessageRuConst.AN_124_FLIGHT_TEXT;
            case AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT -> BotMessageRuConst.AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT;
            case AN_124_IN_YOUR_AIRPORT_NOW_TEXT -> BotMessageRuConst.AN_124_IN_YOUR_AIRPORT_NOW_TEXT;
            case CHOOSING_MODE_TEXT -> BotMessageRuConst.CHOOSING_MODE_TEXT;
            case CHOSEN_MODE_ALL_TEXT -> BotMessageRuConst.CHOSEN_MODE_ALL_TEXT;
            case CHOSEN_MODE_ONLY_AN_124_TEXT -> BotMessageRuConst.CHOSEN_MODE_ONLY_AN_124_TEXT;
            case CHOSEN_MODE_ONLY_WIDE_BODY_TEXT -> BotMessageRuConst.CHOSEN_MODE_ONLY_WIDE_BODY_TEXT;
            case INCORRECT_MODE_TEXT -> BotMessageRuConst.INCORRECT_MODE_TEXT;
            case WAIT_FOR_EMAIL_TEXT -> BotMessageRuConst.WAIT_FOR_EMAIL_TEXT;
            case INVALID_EMAIL_TEXT -> BotMessageRuConst.INVALID_EMAIL_TEXT;
            case INCORRECT_EMAIL_VERIFY_CODE -> BotMessageRuConst.INCORRECT_EMAIL_VERIFY_CODE;
            case VERIFY_EMAIL_TEXT -> BotMessageRuConst.VERIFY_EMAIL_TEXT;
            case USER_NOT_ADDED_EMAIL_TEXT -> BotMessageRuConst.USER_NOT_ADDED_EMAIL_TEXT;
            case EMAIL_NOT_VERIFY_EMAIL -> BotMessageRuConst.EMAIL_NOT_VERIFY_EMAIL;
            case MY_EMAIL_TEXT -> BotMessageRuConst.MY_EMAIL_TEXT;
            case REMOVE_EMAIL_TEXT -> BotMessageRuConst.REMOVE_EMAIL_TEXT;
            case CANCEL_ADDING_EMAIL_TRIGGER -> BotMessageRuConst.CANCEL_ADDING_EMAIL_TRIGGER;
            case CANCEL_ADDING_EMAIL_TEXT -> BotMessageRuConst.CANCEL_ADDING_EMAIL_TEXT;
            case WAIT_FOR_CODE_TEXT -> BotMessageRuConst.WAIT_FOR_CODE_TEXT;
            case SELECT_LANGUAGE_TEXT -> BotMessageRuConst.SELECT_LANGUAGE_TEXT;
            case LANGUAGE_SELECTED_TEXT -> BotMessageRuConst.LANGUAGE_SELECTED_TEXT;
            case INCORRECT_LANGUAGE_TEXT -> BotMessageRuConst.INCORRECT_LANGUAGE_TEXT;
        };
    }

    private String getUaConstant(BotMessageTemplate template) {
        return switch (template) {
            case START_TEXT -> BotMessageUaConst.START_TEXT;
            case CHOOSE_AIRPORT_TEXT -> BotMessageUaConst.CHOOSE_AIRPORT_TEXT;
            case CHANGE_AIRPORT_TEXT -> BotMessageUaConst.CHANGE_AIRPORT_TEXT;
            case CURRENT_AIRPORT_TEXT -> BotMessageUaConst.CURRENT_AIRPORT_TEXT;
            case INVALID_AIRPORT_CODE_TEXT -> BotMessageUaConst.INVALID_AIRPORT_CODE_TEXT;
            case USER_CANNOT_CHOOSE_AIRPORT_TEXT -> BotMessageUaConst.USER_CANNOT_CHOOSE_AIRPORT_TEXT;
            case FLIGHT_TEXT -> BotMessageUaConst.FLIGHT_TEXT;
            case LIVE_FLIGHT_TEXT -> BotMessageUaConst.LIVE_FLIGHT_TEXT;
            case AN_124_FLIGHT_TEXT -> BotMessageUaConst.AN_124_FLIGHT_TEXT;
            case AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT -> BotMessageUaConst.AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT;
            case AN_124_IN_YOUR_AIRPORT_NOW_TEXT -> BotMessageUaConst.AN_124_IN_YOUR_AIRPORT_NOW_TEXT;
            case CHOOSING_MODE_TEXT -> BotMessageUaConst.CHOOSING_MODE_TEXT;
            case CHOSEN_MODE_ALL_TEXT -> BotMessageUaConst.CHOSEN_MODE_ALL_TEXT;
            case CHOSEN_MODE_ONLY_AN_124_TEXT -> BotMessageUaConst.CHOSEN_MODE_ONLY_AN_124_TEXT;
            case CHOSEN_MODE_ONLY_WIDE_BODY_TEXT -> BotMessageUaConst.CHOSEN_MODE_ONLY_WIDE_BODY_TEXT;
            case INCORRECT_MODE_TEXT -> BotMessageUaConst.INCORRECT_MODE_TEXT;
            case WAIT_FOR_EMAIL_TEXT -> BotMessageUaConst.WAIT_FOR_EMAIL_TEXT;
            case INVALID_EMAIL_TEXT -> BotMessageUaConst.INVALID_EMAIL_TEXT;
            case INCORRECT_EMAIL_VERIFY_CODE -> BotMessageUaConst.INCORRECT_EMAIL_VERIFY_CODE;
            case VERIFY_EMAIL_TEXT -> BotMessageUaConst.VERIFY_EMAIL_TEXT;
            case USER_NOT_ADDED_EMAIL_TEXT -> BotMessageUaConst.USER_NOT_ADDED_EMAIL_TEXT;
            case EMAIL_NOT_VERIFY_EMAIL -> BotMessageUaConst.EMAIL_NOT_VERIFY_EMAIL;
            case MY_EMAIL_TEXT -> BotMessageUaConst.MY_EMAIL_TEXT;
            case REMOVE_EMAIL_TEXT -> BotMessageUaConst.REMOVE_EMAIL_TEXT;
            case CANCEL_ADDING_EMAIL_TRIGGER -> BotMessageUaConst.CANCEL_ADDING_EMAIL_TRIGGER;
            case CANCEL_ADDING_EMAIL_TEXT -> BotMessageUaConst.CANCEL_ADDING_EMAIL_TEXT;
            case WAIT_FOR_CODE_TEXT -> BotMessageUaConst.WAIT_FOR_CODE_TEXT;
            case SELECT_LANGUAGE_TEXT -> BotMessageUaConst.SELECT_LANGUAGE_TEXT;
            case LANGUAGE_SELECTED_TEXT -> BotMessageUaConst.LANGUAGE_SELECTED_TEXT;
            case INCORRECT_LANGUAGE_TEXT -> BotMessageUaConst.INCORRECT_LANGUAGE_TEXT;
        };
    }
}
