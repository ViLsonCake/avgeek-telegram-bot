package project.vilsoncake.telegrambot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "geonames")
public class GeonamesProperties {
    private String url;
    private String username;
}

