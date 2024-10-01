package project.vilsoncake.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
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
    public GeonameDto getObject(String q, String country, String language) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("q", q);
        queryParams.add("country", country);
        queryParams.add("lang", language);
        queryParams.add("featureClass", "P");
        queryParams.add("maxRows", "1");
        queryParams.add("username", geonamesProperties.getUsername());

        try {
            GeonamesDto geonamesDto = geoNamesWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParams(queryParams)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(GeonamesDto.class)
                    .block();
            if (geonamesDto.getGeonames() != null && !geonamesDto.getGeonames().isEmpty()) {
                return geonamesDto.getGeonames().get(0);
            }
        } catch (WebClientRequestException ignored) {

        }

        GeonameDto geonameDto = new GeonameDto();
        geonameDto.setName(q);
        geonameDto.setCountryName(country);
        geonameDto.setLatitude(0);
        geonameDto.setLongitude(0);

        return geonameDto;
    }
}
