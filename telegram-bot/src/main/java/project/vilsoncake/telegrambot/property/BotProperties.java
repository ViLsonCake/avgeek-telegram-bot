package project.vilsoncake.telegrambot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "bot")
public class BotProperties {
    private String name;
    private String token;
    private long creatorId;
    private boolean enableAn124Flights;
    private boolean enableScheduledFlights;
    private boolean enableLandingFlights;
    private List<String> landingWhitelistUsers;
}
