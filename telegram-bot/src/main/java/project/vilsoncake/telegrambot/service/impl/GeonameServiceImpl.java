package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.telegrambot.dto.GeonameDto;
import project.vilsoncake.telegrambot.dto.GeonamesDto;
import project.vilsoncake.telegrambot.property.GeonamesProperties;
import project.vilsoncake.telegrambot.service.GeonameService;

@Service
@RequiredArgsConstructor
public class GeonameServiceImpl implements GeonameService {

    private final WebClient geoNamesWebClient;
    private final GeonamesProperties geonamesProperties;

    @Override
    public GeonameDto getObject(String q, String language) {
        return geoNamesWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", q)
                        .queryParam("lang", language)
                        .queryParam("maxRows", 1)
                        .queryParam("username", geonamesProperties.getUsername())
                        .build()
                )
                .retrieve()
                .bodyToMono(GeonamesDto.class)
                .block().getGeonames().get(0);
    }
}
