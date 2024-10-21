package project.vilsoncake.telegrambot.utils;

import org.springframework.stereotype.Component;

@Component
public class LoggingUtils {

    public String getFinishedProcessTime(long startTime, long endTime) {
        double timeInSeconds = (double) (endTime - startTime) / 1000;

        if (timeInSeconds < 60) {
            return String.format("Process time: %s seconds.", timeInSeconds);
        }

        int timeInMinutes = (int) (timeInSeconds / 60);
        int remainder = (int) (timeInSeconds % 60);

        return String.format("Process time: %s minutes %s seconds.", timeInMinutes, remainder);
    }
}
