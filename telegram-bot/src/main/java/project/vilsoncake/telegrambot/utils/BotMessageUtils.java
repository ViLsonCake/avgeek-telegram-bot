package project.vilsoncake.telegrambot.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.telegrambot.constant.BotMessageEngConst;
import project.vilsoncake.telegrambot.constant.BotMessageRuConst;
import project.vilsoncake.telegrambot.constant.BotMessageUkConst;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.BotMessageTemplate;

@Component
public class BotMessageUtils {

    public String getMessageByLanguage(BotMessageTemplate template, BotLanguage language) {
        return switch (language) {
            case ENG -> getEngConstant(template);
            case RU -> getRuConstant(template);
            case UK -> getUkConstant(template);
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
            case FLIGHT_WITHOUT_REGISTRATION_TEXT -> BotMessageEngConst.FLIGHT_WITHOUT_REGISTRATION_TEXT;
            case DEPARTED_FLIGHT_TEXT -> BotMessageEngConst.DEPARTED_FLIGHT_TEXT;
            case LANDING_FLIGHT_TEXT -> BotMessageEngConst.LANDING_FLIGHT_TEXT;
            case AN_124_FLIGHT_TEXT -> BotMessageEngConst.AN_124_FLIGHT_TEXT;
            case AN_124_ON_GROUND_BEFORE_FLIGHT_TEXT -> BotMessageEngConst.AN_124_ON_GROUND_BEFORE_FLIGHT_TEXT;
            case AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT -> BotMessageEngConst.AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT;
            case AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT -> BotMessageEngConst.AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_UNKNOWN_AIRPORT_TEXT -> BotMessageEngConst.AN_124_LANDING_UNKNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_KNOWN_AIRPORT_TEXT -> BotMessageEngConst.AN_124_LANDING_KNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT -> BotMessageEngConst.AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT -> BotMessageEngConst.AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT;
            case AN_124_TAKEOFF_NEAR_TEXT -> BotMessageEngConst.AN_124_TAKEOFF_NEAR_TEXT;
            case AN_124_TAKEOFF_FROM_TEXT -> BotMessageEngConst.AN_124_TAKEOFF_FROM_TEXT;
            case AN_124_LIKELY_TO_LAND_AIRPORT_TEXT -> BotMessageEngConst.AN_124_LIKELY_TO_LAND_AIRPORT_TEXT;
            case AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT -> BotMessageEngConst.AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT;
            case AN_124_IN_YOUR_AIRPORT_NOW_TEXT -> BotMessageEngConst.AN_124_IN_YOUR_AIRPORT_NOW_TEXT;
            case CHOOSING_MODE_TEXT -> BotMessageEngConst.CHOOSING_MODE_TEXT;
            case CHOSEN_MODE_ALL_TEXT -> BotMessageEngConst.CHOSEN_MODE_ALL_TEXT;
            case CHOSEN_MODE_ONLY_AN_124_TEXT -> BotMessageEngConst.CHOSEN_MODE_ONLY_AN_124_TEXT;
            case CHOSEN_MODE_ONLY_WIDE_BODY_TEXT -> BotMessageEngConst.CHOSEN_MODE_ONLY_WIDE_BODY_TEXT;
            case CHOSEN_MODE_MUTE_TEXT -> BotMessageEngConst.CHOSEN_MODE_MUTE_TEXT;
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
            case FLIGHT_WITHOUT_REGISTRATION_TEXT -> BotMessageRuConst.FLIGHT_WITHOUT_REGISTRATION_TEXT;
            case DEPARTED_FLIGHT_TEXT -> BotMessageRuConst.DEPARTED_FLIGHT_TEXT;
            case LANDING_FLIGHT_TEXT -> BotMessageRuConst.LANDING_FLIGHT_TEXT;
            case AN_124_FLIGHT_TEXT -> BotMessageRuConst.AN_124_FLIGHT_TEXT;
            case AN_124_ON_GROUND_BEFORE_FLIGHT_TEXT -> BotMessageRuConst.AN_124_ON_GROUND_BEFORE_FLIGHT_TEXT;
            case AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT -> BotMessageRuConst.AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT;
            case AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT -> BotMessageRuConst.AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT -> BotMessageRuConst.AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT -> BotMessageRuConst.AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_UNKNOWN_AIRPORT_TEXT -> BotMessageRuConst.AN_124_LANDING_UNKNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_KNOWN_AIRPORT_TEXT -> BotMessageRuConst.AN_124_LANDING_KNOWN_AIRPORT_TEXT;
            case AN_124_TAKEOFF_NEAR_TEXT -> BotMessageRuConst.AN_124_TAKEOFF_NEAR_TEXT;
            case AN_124_TAKEOFF_FROM_TEXT -> BotMessageRuConst.AN_124_TAKEOFF_FROM_TEXT;
            case AN_124_LIKELY_TO_LAND_AIRPORT_TEXT -> BotMessageRuConst.AN_124_LIKELY_TO_LAND_AIRPORT_TEXT;
            case AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT -> BotMessageRuConst.AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT;
            case AN_124_IN_YOUR_AIRPORT_NOW_TEXT -> BotMessageRuConst.AN_124_IN_YOUR_AIRPORT_NOW_TEXT;
            case CHOOSING_MODE_TEXT -> BotMessageRuConst.CHOOSING_MODE_TEXT;
            case CHOSEN_MODE_ALL_TEXT -> BotMessageRuConst.CHOSEN_MODE_ALL_TEXT;
            case CHOSEN_MODE_ONLY_AN_124_TEXT -> BotMessageRuConst.CHOSEN_MODE_ONLY_AN_124_TEXT;
            case CHOSEN_MODE_ONLY_WIDE_BODY_TEXT -> BotMessageRuConst.CHOSEN_MODE_ONLY_WIDE_BODY_TEXT;
            case CHOSEN_MODE_MUTE_TEXT -> BotMessageRuConst.CHOSEN_MODE_MUTE_TEXT;
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

    private String getUkConstant(BotMessageTemplate template) {
        return switch (template) {
            case START_TEXT -> BotMessageUkConst.START_TEXT;
            case CHOOSE_AIRPORT_TEXT -> BotMessageUkConst.CHOOSE_AIRPORT_TEXT;
            case CHANGE_AIRPORT_TEXT -> BotMessageUkConst.CHANGE_AIRPORT_TEXT;
            case CURRENT_AIRPORT_TEXT -> BotMessageUkConst.CURRENT_AIRPORT_TEXT;
            case INVALID_AIRPORT_CODE_TEXT -> BotMessageUkConst.INVALID_AIRPORT_CODE_TEXT;
            case USER_CANNOT_CHOOSE_AIRPORT_TEXT -> BotMessageUkConst.USER_CANNOT_CHOOSE_AIRPORT_TEXT;
            case FLIGHT_TEXT -> BotMessageUkConst.FLIGHT_TEXT;
            case FLIGHT_WITHOUT_REGISTRATION_TEXT -> BotMessageUkConst.FLIGHT_WITHOUT_REGISTRATION_TEXT;
            case DEPARTED_FLIGHT_TEXT -> BotMessageUkConst.DEPARTED_FLIGHT_TEXT;
            case LANDING_FLIGHT_TEXT -> BotMessageUkConst.LANDING_FLIGHT_TEXT;
            case AN_124_FLIGHT_TEXT -> BotMessageUkConst.AN_124_FLIGHT_TEXT;
            case AN_124_ON_GROUND_BEFORE_FLIGHT_TEXT -> BotMessageUkConst.AN_124_ON_GROUND_BEFORE_TEXT;
            case AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT -> BotMessageUkConst.AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT;
            case AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT -> BotMessageUkConst.AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_UNKNOWN_AIRPORT_TEXT -> BotMessageUkConst.AN_124_LANDING_UNKNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_KNOWN_AIRPORT_TEXT -> BotMessageUkConst.AN_124_LANDING_KNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT -> BotMessageUkConst.AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT;
            case AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT -> BotMessageUkConst.AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT;
            case AN_124_TAKEOFF_NEAR_TEXT -> BotMessageUkConst.AN_124_TAKEOFF_NEAR_TEXT;
            case AN_124_TAKEOFF_FROM_TEXT -> BotMessageUkConst.AN_124_TAKEOFF_FROM_TEXT;
            case AN_124_LIKELY_TO_LAND_AIRPORT_TEXT -> BotMessageUkConst.AN_124_LIKELY_TO_LAND_AIRPORT_TEXT;
            case AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT -> BotMessageUkConst.AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT;
            case AN_124_IN_YOUR_AIRPORT_NOW_TEXT -> BotMessageUkConst.AN_124_IN_YOUR_AIRPORT_NOW_TEXT;
            case CHOOSING_MODE_TEXT -> BotMessageUkConst.CHOOSING_MODE_TEXT;
            case CHOSEN_MODE_ALL_TEXT -> BotMessageUkConst.CHOSEN_MODE_ALL_TEXT;
            case CHOSEN_MODE_ONLY_AN_124_TEXT -> BotMessageUkConst.CHOSEN_MODE_ONLY_AN_124_TEXT;
            case CHOSEN_MODE_ONLY_WIDE_BODY_TEXT -> BotMessageUkConst.CHOSEN_MODE_ONLY_WIDE_BODY_TEXT;
            case CHOSEN_MODE_MUTE_TEXT -> BotMessageUkConst.CHOSEN_MODE_MUTE_TEXT;
            case INCORRECT_MODE_TEXT -> BotMessageUkConst.INCORRECT_MODE_TEXT;
            case WAIT_FOR_EMAIL_TEXT -> BotMessageUkConst.WAIT_FOR_EMAIL_TEXT;
            case INVALID_EMAIL_TEXT -> BotMessageUkConst.INVALID_EMAIL_TEXT;
            case INCORRECT_EMAIL_VERIFY_CODE -> BotMessageUkConst.INCORRECT_EMAIL_VERIFY_CODE;
            case VERIFY_EMAIL_TEXT -> BotMessageUkConst.VERIFY_EMAIL_TEXT;
            case USER_NOT_ADDED_EMAIL_TEXT -> BotMessageUkConst.USER_NOT_ADDED_EMAIL_TEXT;
            case EMAIL_NOT_VERIFY_EMAIL -> BotMessageUkConst.EMAIL_NOT_VERIFY_EMAIL;
            case MY_EMAIL_TEXT -> BotMessageUkConst.MY_EMAIL_TEXT;
            case REMOVE_EMAIL_TEXT -> BotMessageUkConst.REMOVE_EMAIL_TEXT;
            case CANCEL_ADDING_EMAIL_TRIGGER -> BotMessageUkConst.CANCEL_ADDING_EMAIL_TRIGGER;
            case CANCEL_ADDING_EMAIL_TEXT -> BotMessageUkConst.CANCEL_ADDING_EMAIL_TEXT;
            case WAIT_FOR_CODE_TEXT -> BotMessageUkConst.WAIT_FOR_CODE_TEXT;
            case SELECT_LANGUAGE_TEXT -> BotMessageUkConst.SELECT_LANGUAGE_TEXT;
            case LANGUAGE_SELECTED_TEXT -> BotMessageUkConst.LANGUAGE_SELECTED_TEXT;
            case INCORRECT_LANGUAGE_TEXT -> BotMessageUkConst.INCORRECT_LANGUAGE_TEXT;
        };
    }
}
