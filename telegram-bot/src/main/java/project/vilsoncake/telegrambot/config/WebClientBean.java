package project.vilsoncake.telegrambot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.telegrambot.property.FlightradarApiProperties;

@Configuration
@RequiredArgsConstructor
public class WebClientBean {

    private final FlightradarApiProperties flightradarApiProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("x-api-key", flightradarApiProperties.getApiKey())
                .baseUrl(flightradarApiProperties.getUrl())
                .build();
    }
}
