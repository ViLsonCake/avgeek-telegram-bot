package project.vilsoncake.telegrambot.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import project.vilsoncake.telegrambot.dto.AircraftFamilyDto;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class AircraftBeans {

    @Bean
    public List<AircraftFamilyDto> aircraft() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<AircraftFamilyDto>> typeReference = new TypeReference<>() {};

        try {
            Resource resource = new ClassPathResource("/json/aircraft-families.json");
            List<AircraftFamilyDto> data = objectMapper.readValue(resource.getContentAsString(Charset.defaultCharset()), typeReference);
            log.info("Aircraft json data loaded");
            return data;
        } catch (IOException e) {
            log.error("Failed to load aircraft data");
            throw new RuntimeException("Failed to load aircraft data");
        }
    }

    @Bean
    public List<Map<String, List<String>>> aircraftFamiliesCodes() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Map<String, List<String>>>> typeReference = new TypeReference<>() {};

        try {
            Resource resource = new ClassPathResource("/json/aircraft-families-codes.json");
            List<Map<String, List<String>>> data = objectMapper.readValue(resource.getContentAsString(Charset.defaultCharset()), typeReference);
            log.info("Aircraft families codes json data loaded");
            return data;
        } catch (IOException e) {
            log.error("Failed to load aircraft families codes data");
            throw new RuntimeException("Failed to load aircraft families codes data");
        }
    }
}
