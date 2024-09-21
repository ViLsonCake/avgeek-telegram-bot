package project.vilsoncake.telegrambot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static project.vilsoncake.telegrambot.constant.CommandNamesConst.MARKDOWN_PARSE_MODE;

public class BaseBotMessage {

    public static SendMessage getBaseBotMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setDisableWebPagePreview(true);

        return message;
    }
}
