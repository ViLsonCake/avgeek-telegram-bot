package project.vilsoncake.telegrambot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.telegrambot.property.FlightradarApiProperties;
import project.vilsoncake.telegrambot.property.GeonamesProperties;

@Configuration
@RequiredArgsConstructor
public class WebClientBeans {

    private final FlightradarApiProperties flightradarApiProperties;
    private final GeonamesProperties geonamesProperties;

    @Bean("apiWebClient")
    public WebClient apiWebClient() {
        return WebClient.builder()
                .defaultHeader("x-api-key", flightradarApiProperties.getApiKey())
                .baseUrl(flightradarApiProperties.getUrl())
                .build();
    }

    @Bean("geoNamesWebClient")
    public WebClient geoNamesWebClient() {
        return WebClient.builder()
                .baseUrl(geonamesProperties.getUrl())
                .build();
    }
}
