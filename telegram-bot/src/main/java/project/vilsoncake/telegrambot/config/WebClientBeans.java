package project.vilsoncake.telegrambot.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.telegrambot.property.FlightradarApiProperties;
import project.vilsoncake.telegrambot.property.GeonamesProperties;
import reactor.netty.http.client.HttpClient;

@Configuration
@RequiredArgsConstructor
public class WebClientBeans {

    private final FlightradarApiProperties flightradarApiProperties;
    private final GeonamesProperties geonamesProperties;

    @Bean("apiWebClient")
    public WebClient apiWebClient() {
        return WebClient.builder()
                .defaultHeader("x-api-key", flightradarApiProperties.getApiKey())
                .defaultHeader(HttpHeaders.AUTHORIZATION, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .doOnConnected(connection -> connection
                                .addHandlerLast(new ReadTimeoutHandler(5))
                                .addHandlerLast(new WriteTimeoutHandler(5)))))
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
