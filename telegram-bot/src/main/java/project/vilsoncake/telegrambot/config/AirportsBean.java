package project.vilsoncake.telegrambot.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import project.vilsoncake.telegrambot.dto.AirportDto;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@Configuration
public class AirportsBean {

    @Bean
    public List<AirportDto> airports() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<AirportDto>> typeReference = new TypeReference<>() {};

        try {
            Resource resource = new ClassPathResource("/json/airports.json");
            List<AirportDto> data = objectMapper.readValue(resource.getContentAsString(Charset.defaultCharset()), typeReference);
            log.info("Airports json data loaded");
            return data;
        } catch (IOException e) {
            log.error("Failed to load airport data");
            throw new RuntimeException("Failed to load airport data");
        }
    }
}
