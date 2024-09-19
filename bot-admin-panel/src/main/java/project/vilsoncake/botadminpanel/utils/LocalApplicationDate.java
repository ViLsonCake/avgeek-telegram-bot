package project.vilsoncake.botadminpanel.utils;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Getter
public class LocalApplicationDate {

    private final String time;

    public LocalApplicationDate(Date date, String timezone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss-dd.MM.yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        this.time = simpleDateFormat.format(date);
    }
}
