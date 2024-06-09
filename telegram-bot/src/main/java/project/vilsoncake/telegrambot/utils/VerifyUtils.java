package project.vilsoncake.telegrambot.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Pattern;

import static project.vilsoncake.telegrambot.constant.RegexConst.EMAIL_REGEX;

@Component
public class VerifyUtils {

    public int generateCode() {
        return new Random().nextInt(100_000, 1_000_000);
    }

    public boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }
}
