package project.vilsoncake.telegrambot.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.telegrambot.constant.MailMessageEngConst;
import project.vilsoncake.telegrambot.constant.MailMessageRuConst;
import project.vilsoncake.telegrambot.constant.MailMessageUaConst;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate;

@Component
public class MailMessageUtils {

    public String getMessageByLanguage(MailMessageTemplate template, BotLanguage language) {
        return switch (language) {
            case ENG -> getEngConstant(template);
            case RU -> getRuConstant(template);
            case UK -> getUaConstant(template);
        };
    }

    private String getEngConstant(MailMessageTemplate template) {
        return switch (template) {
            case CODE_MESSAGE_SUBJECT -> MailMessageEngConst.CODE_MESSAGE_SUBJECT;
            case CODE_MESSAGE_TEXT -> MailMessageEngConst.CODE_MESSAGE_TEXT;
            case AN_124_IN_AIRPORT_MESSAGE_SUBJECT -> MailMessageEngConst.AN_124_IN_AIRPORT_MESSAGE_SUBJECT;
            case AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT -> MailMessageEngConst.AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT;
        };
    }

    private String getRuConstant(MailMessageTemplate template) {
        return switch (template) {
            case CODE_MESSAGE_SUBJECT -> MailMessageRuConst.CODE_MESSAGE_SUBJECT;
            case CODE_MESSAGE_TEXT -> MailMessageRuConst.CODE_MESSAGE_TEXT;
            case AN_124_IN_AIRPORT_MESSAGE_SUBJECT -> MailMessageRuConst.AN_124_IN_AIRPORT_MESSAGE_SUBJECT;
            case AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT -> MailMessageRuConst.AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT;
        };
    }

    private String getUaConstant(MailMessageTemplate template) {
        return switch (template) {
            case CODE_MESSAGE_SUBJECT -> MailMessageUaConst.CODE_MESSAGE_SUBJECT;
            case CODE_MESSAGE_TEXT -> MailMessageUaConst.CODE_MESSAGE_TEXT;
            case AN_124_IN_AIRPORT_MESSAGE_SUBJECT -> MailMessageUaConst.AN_124_IN_AIRPORT_MESSAGE_SUBJECT;
            case AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT -> MailMessageUaConst.AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT;
        };
    }
}
