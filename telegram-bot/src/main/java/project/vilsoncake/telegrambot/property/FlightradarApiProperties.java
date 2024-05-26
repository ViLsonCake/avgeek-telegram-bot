package project.vilsoncake.telegrambot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "flightradar-api")
public class FlightradarApiProperties {
    private String apiKey;
    private String url;
}
