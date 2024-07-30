package project.vilsoncake.telegrambot.service;

import project.vilsoncake.telegrambot.dto.GeonameDto;

public interface GeonameService {
    GeonameDto getObject(String q, String country, String language);
}
