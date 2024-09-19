package project.vilsoncake.botadminpanel.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Getter
@Setter
public class LocalApplicationDate {

    @Setter(AccessLevel.NONE)
    private final String time;

    @Value("${app.timezone}")
    private String timezone;

    public LocalApplicationDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss-dd.MM.yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        this.time = simpleDateFormat.format(date);
    }
}
