package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public GeonameDto getObject(String q, String language, boolean isAirport) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("q", q);
        queryParams.add("lang", language);
        queryParams.add("maxRows", "1");
        queryParams.add("username", geonamesProperties.getUsername());

        if (isAirport) {
            queryParams.add("featureCode", "AIRP");
        }

        return geoNamesWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParams(queryParams)
                        .build()
                )
                .retrieve()
                .bodyToMono(GeonamesDto.class)
                .block().getGeonames().get(0);
    }
}
