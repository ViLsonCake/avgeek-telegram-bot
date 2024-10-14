package project.vilsoncake.telegrambot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.telegrambot.constant.BotMessageEngConst;
import project.vilsoncake.telegrambot.dto.*;
import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;
import project.vilsoncake.telegrambot.exception.AirportNotFoundException;
import project.vilsoncake.telegrambot.exception.FlightNotFoundException;
import project.vilsoncake.telegrambot.service.FlightService;
import project.vilsoncake.telegrambot.service.GeonameService;
import project.vilsoncake.telegrambot.service.MailService;
import project.vilsoncake.telegrambot.service.UserService;
import project.vilsoncake.telegrambot.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static project.vilsoncake.telegrambot.constant.BotMessageEngConst.ANTONOV_AIRLINES_NAME;
import static project.vilsoncake.telegrambot.constant.CommandNamesConst.MARKDOWN_PARSE_MODE;
import static project.vilsoncake.telegrambot.constant.NumberConst.*;
import static project.vilsoncake.telegrambot.entity.enumerated.BotMessageTemplate.*;
import static project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate.AN_124_IN_AIRPORT_MESSAGE_SUBJECT;
import static project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate.AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleSender {

    private final UserService userService;
    private final FlightService flightService;
    private final MailService mailService;
    private final GeonameService geonameService;
    private final BotMessageUtils botMessageUtils;
    private final MailMessageUtils mailMessageUtils;
    private final AirportsUtils airportsUtils;
    private final AircraftUtils aircraftUtils;
    private final List<Map<String, List<String>>> aircraftFamiliesCodes;
    private final UnitsUtils unitsUtils;
    private final WebClient apiWebClient;
    private final BotSender botSender;

    @Transactional
    @Scheduled(fixedDelay = SCHEDULED_FLIGHTS_CHECK_DELAY_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void sendNewScheduledAndLiveWideBodyFlights() throws InterruptedException {
        log.info("Schedule sending wide body flights started...");

        List<String> uniqueAirports = userService.findUniqueAirports();
        Map<String, FlightsDto> uniqueAirportsFlights = new HashMap<>();

        for (String airport : uniqueAirports) {
            Thread.sleep(REQUESTS_DELAY_IN_MILLIS);

            FlightsDto flightsDto;

            try {
                flightsDto = apiWebClient.get()
                        .uri("/airport/flights/" + airport.toUpperCase())
                        .retrieve()
                        .bodyToMono(FlightsDto.class)
                        .block();
            } catch (WebClientRequestException e) {
                log.error("Error when requesting API for scheduled flights. URL: {}, Message: {}", e.getUri(), e.getMessage());
                continue;
            } catch (WebClientResponseException e) {
                log.error("Response API error for scheduled flights. URL: {}, Status code: {}, Status text: {}, Message: {}", e.getRequest().getURI(), e.getStatusCode(), e.getStatusText(), e.getMessage());
                continue;
            }

            uniqueAirportsFlights.put(airport.toUpperCase(), flightsDto);
        }

        List<UserEntity> users = userService.findAllUsers();

        for (UserEntity user : users) {

            if (!user.getState().equals(UserState.CHOSEN_AIRPORT)) {
                continue;
            }

            if (user.getBotMode().equals(BotMode.ALL) || user.getBotMode().equals(BotMode.ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS)) {
                FlightsDto flightsDto = uniqueAirportsFlights.get(user.getAirport().toUpperCase());

                if (flightsDto == null) {
                    continue;
                }

                for (ScheduledFlightDataDto flight : flightsDto.getFlights()) {
                    List<String> userSelectedAircraftFamiliesCodes = aircraftUtils.getUserSelectedAircraftFamiliesCodes(user.getAircraft(), aircraftFamiliesCodes);

                    if (!user.getAircraft().isEmpty()) {
                        if (!userSelectedAircraftFamiliesCodes.contains(flight.getCode())) {
                            continue;
                        }
                    }

                    AirportDto airportDto = airportsUtils.getAirportByIataCode(flight.getIata());
                    GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), airportDto.getCountry(), user.getBotLanguage().name());

                    if (!flightService.existsByUserAndFlightId(user, flight.getId())) {
                        FlightEntity flightEntity = new FlightEntity(flight.getId(), user);
                        flightEntity.setRegistration(flight.getRegistration());

                        SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                        message.setChatId(user.getChatId());
                        message.setParseMode(MARKDOWN_PARSE_MODE);

                        if (flight.getRegistration() == null || flight.getRegistration().isBlank()) {
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(FLIGHT_WITHOUT_REGISTRATION_TEXT, user.getBotLanguage()),
                                    airportDto.getName(), flight.getIata(), geonameCityDto.getCountryName(), flight.getAircraft(), flight.getAirlineName(), user.getAirport()
                            ));
                        } else {
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(FLIGHT_TEXT, user.getBotLanguage()),
                                    flight.getRegistration(), airportDto.getName(), flight.getIata(), geonameCityDto.getCountryName(), flight.getAircraft(), flight.getAirlineName(), flight.getRegistration(), user.getAirport()
                            ));
                        }

                        botSender.sendMessage(message);

                        flightService.addFlightToUser(flightEntity);
                    } else if (flightService.existsByUserAndFlightId(user, flight.getId()) && flight.isLive() && !flightService.findByUserAndFlightId(user, flight.getId()).isActive()) {
                        flightService.changeFlightActive(flightService.findByUserAndFlightId(user, flight.getId()), true);

                        FlightDataDto flightDataDto;

                        try {
                            flightDataDto = apiWebClient.get()
                                    .uri("/registration/" + flight.getRegistration() + "/" + user.getAirport())
                                    .retrieve()
                                    .bodyToMono(FlightDataDto.class)
                                    .block();

                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setChatId(user.getChatId());
                            message.setParseMode(MARKDOWN_PARSE_MODE);
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(DEPARTED_FLIGHT_TEXT, user.getBotLanguage()),
                                    flight.getRegistration(), airportDto.getName(), flight.getIata(), geonameCityDto.getCountryName(), flight.getAircraft(), flight.getAirlineName(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flightDataDto.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flightDataDto.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flightDataDto.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flight.getRegistration(), flight.getCallsign(), flight.getId()
                            ));

                            botSender.sendMessage(message);
                        } catch (WebClientRequestException | WebClientResponseException ignored) {

                        }
                    }
                }
            }
        }
        log.info("Schedule sending wide body flights finished.");
    }

    @Transactional
    @Scheduled(fixedDelay = LANDING_FLIGHTS_CHECK_DELAY_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void sendLandingWideBodyFlights() throws InterruptedException {
        log.info("Schedule sending landing flights started...");

        List<FlightEntity> uniqueRegistrations = flightService.findUniqueFlightsRegistrations();
        List<FlightDataDto> flightDataDtos = new ArrayList<>();

        for (FlightEntity flightEntity : uniqueRegistrations) {
            Thread.sleep(REQUESTS_DELAY_IN_MILLIS);

            FlightDataDto flight;
            try {
                flight = apiWebClient.get()
                        .uri("/registration/" + flightEntity.getRegistration() + "/" + flightEntity.getUser().getAirport())
                        .retrieve()
                        .bodyToMono(FlightDataDto.class)
                        .block();
            } catch (WebClientRequestException e) {
                log.error("Error when requesting API for landing flights. URL: {}, Message: {}", e.getUri(), e.getMessage());
                continue;
            } catch (WebClientResponseException e) {
                if (!e.getStatusCode().equals(HttpStatusCode.valueOf(HttpStatus.SC_NOT_FOUND))) {
                    log.error("Response API error for landing flights. URL: {}, Status code: {}, Status text: {}, Message: {}", e.getRequest().getURI(), e.getStatusCode(), e.getStatusText(), e.getMessage());
                }
                continue;
            }

            flightDataDtos.add(flight);
        }

        List<UserEntity> users = userService.findAllUsers();

        for (UserEntity user : users) {

            if (!user.getState().equals(UserState.CHOSEN_AIRPORT)) {
                continue;
            }

            if (user.getBotMode().equals(BotMode.ALL) || user.getBotMode().equals(BotMode.ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS)) {
                for (FlightDataDto flight : flightDataDtos) {

                    if (flight.getOriginAirportIata() == null || flight.getOriginAirportIata().isBlank() || !flight.getDestinationAirportIata().equalsIgnoreCase(user.getAirport())) {
                        continue;
                    }

                    List<String> userSelectedAircraftFamiliesCodes = aircraftUtils.getUserSelectedAircraftFamiliesCodes(user.getAircraft(), aircraftFamiliesCodes);

                    if (!user.getAircraft().isEmpty()) {
                        if (userSelectedAircraftFamiliesCodes.contains(flight.getCode())) {
                            continue;
                        }
                    }

                    FlightEntity flightEntity;

                    try {
                        flightEntity = flightService.findByUserAndRegistration(user, flight.getRegistration());
                    } catch (FlightNotFoundException e) {
                        continue;
                    }

                    if (flightEntity.isActive() && flightEntity.getRegistration() != null && !flightEntity.getRegistration().isBlank() && !flightEntity.isLanding()) {
                        if (flight.getVerticalSpeed() < APPROACHING_VERTICAL_SPEED_IN_FPM) {
                            AirportDto airportDto = airportsUtils.getAirportByIataCode(flight.getOriginAirportIata());
                            GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), airportDto.getCountry(), user.getBotLanguage().name());
                            flightService.changeFlightLanding(flightEntity, true);

                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(LANDING_FLIGHT_TEXT, user.getBotLanguage()),
                                    flightEntity.getRegistration(), airportDto.getName(), flight.getOriginAirportIata(), geonameCityDto.getCountryName(), flight.getAircraft(), flight.getAirline(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flightEntity.getRegistration(), flight.getCallsign(), flight.getId()
                            ));

                            botSender.sendMessage(message);
                        }
                    }
                }
            }
        }
        log.info("Schedule sending landing flights finished.");
    }

    @Scheduled(fixedDelay = AN_124_FLIGHTS_CHECK_DELAY_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void sendNewAn124Flights() {
        log.info("Schedule sending An-124 flights started...");
        List<UserEntity> users = userService.findAllUsers();

        An124FlightsDto an124FlightsDto;

        try {
            an124FlightsDto = apiWebClient.get()
                    .uri("/aircraft/" + BotMessageEngConst.AN_124_CODE)
                    .retrieve()
                    .bodyToMono(An124FlightsDto.class)
                    .block();
        } catch (WebClientRequestException e) {
            log.error("Error when requesting API for An-124 flights. URL: {}, Message: {}", e.getUri(), e.getMessage());
            return;
        } catch (WebClientResponseException e) {
            log.error("Response API error for An-124 flights. URL: {}, Status code: {}, Status text: {}, Message: {}", e.getRequest().getURI(), e.getStatusCode(), e.getStatusText(), e.getMessage());
            return;
        }

        for (UserEntity user : users) {

            if (!user.getState().equals(UserState.CHOSEN_AIRPORT)) {
                continue;
            }

            if (user.getBotMode().equals(BotMode.ALL) || user.getBotMode().equals(BotMode.ONLY_AN_124_FLIGHTS)) {
                for (An124FlightDto flight : an124FlightsDto.getFlights()) {
                    if (flight.getAirline().isBlank()) {
                        flight.setAirline(ANTONOV_AIRLINES_NAME);
                    }

                    AirportDto userAirport = airportsUtils.getAirportByIataCode(user.getAirport());
                    flight.setDistance(airportsUtils.calculateDistanceByCoordinates(flight.getLatitude(), flight.getLongitude(), userAirport.getLatitude(), userAirport.getLongitude()));
                    if (!flightService.existsByUserAndFlightId(user, flight.getId())) {
                        FlightEntity flightEntity = new FlightEntity(flight.getId(), user);
                        flightEntity.setDistance(flight.getDistance());

                        if (flight.getAltitude() == ON_GROUND_ALTITUDE && flight.getDistance() < FLIGHT_IN_AIRPORT_DISTANCE_IN_KM) {
                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_IN_YOUR_AIRPORT_NOW_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() < FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M && flight.getVerticalSpeed() < APPROACHING_VERTICAL_SPEED_IN_FPM) {
                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LIKELY_TO_LAND_AIRPORT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() > FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M) {
                            flightService.changeFlightFlyingNear(flightEntity, true);

                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getAltitude() == ON_GROUND_ALTITUDE) {
                            try {
                                AirportDto airportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), ON_GROUND_RADIUS);
                                GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), airportDto.getCountry(), user.getBotLanguage().name());

                                flightService.changeFlightDepartureAirport(flightEntity, airportDto.getIata());

                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_ON_GROUND_BEFORE_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), airportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAirline(),
                                        unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                        flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                        unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                        unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                        unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                        flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        } else if (flight.getAltitude() < LOW_ALTITUDE_IN_M) {
                            try {
                                AirportDto airportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), CLOSE_TO_AIRPORT_RANGE_IN_KM);
                                GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), airportDto.getCountry(), user.getBotLanguage().name());
                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());

                                if (flight.getVerticalSpeed() > 0) {
                                    flightService.changeFlightTookOff(flightEntity, true);
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_TAKEOFF_NEAR_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), airportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAirline(),
                                            unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            flight.getCallsign(), flight.getId()
                                    ));
                                } else {
                                    flightService.changeFlightLanding(flightEntity, true);
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LANDING_UNKNOWN_AIRPORT_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), airportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAirline(),
                                            unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            flight.getCallsign(), flight.getId()
                                    ));
                                }

                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                        unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                        unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                        unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                        flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        } else {
                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        }

                        flightService.addFlightToUser(flightEntity);
                    } else {
                        FlightEntity flightEntity = flightService.findByUserAndFlightId(user, flight.getId());

                        if (flight.getAltitude() == ON_GROUND_ALTITUDE && flight.getDistance() < FLIGHT_IN_AIRPORT_DISTANCE_IN_KM) {
                            flightService.changeFlightDistance(flightEntity, flight.getDistance());
                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_IN_YOUR_AIRPORT_NOW_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);

                            if (user.isEmailVerified()) {
                                mailService.sendMessage(
                                        user.getEmail(),
                                        mailMessageUtils.getMessageByLanguage(AN_124_IN_AIRPORT_MESSAGE_SUBJECT, user.getBotLanguage()),
                                        String.format(mailMessageUtils.getMessageByLanguage(AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT, user.getBotLanguage()),
                                                flight.getAirline(),
                                                unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                                unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                                unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                                flight.getCallsign(), flight.getId())
                                );
                            }

                        } else if (flight.getDistance() < flightEntity.getDistance() && flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() < FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M && !flightEntity.isActive() && flight.getVerticalSpeed() < APPROACHING_VERTICAL_SPEED_IN_FPM) {
                            flightService.changeFlightActive(flightEntity, true);
                            flightService.changeFlightDistance(flightEntity, flight.getDistance());
                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LIKELY_TO_LAND_AIRPORT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (!flightEntity.isFlyingNear() && flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() > FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M) {
                            flightService.changeFlightFlyingNear(flightEntity, true);

                            SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                    unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                    unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                    flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getAltitude() == ON_GROUND_ALTITUDE && flightEntity.isTookOff() && !flightEntity.isOnGround()) {
                            try {
                                flightService.changeFlightOnGround(flightEntity, true);
                                AirportDto destinationAirportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), ON_GROUND_RADIUS);
                                GeonameDto destinationGeonameCityDto = geonameService.getObject(destinationAirportDto.getCity(), destinationAirportDto.getCountry(), user.getBotLanguage().name());
                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());

                                if (flightEntity.getDepartureAirport() != null) {
                                    AirportDto departureAirportDto = airportsUtils.getAirportByIataCode(flightEntity.getDepartureAirport());
                                    GeonameDto departureGeonameCityDto = geonameService.getObject(departureAirportDto.getCity(), departureAirportDto.getCountry(), user.getBotLanguage().name());
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_ON_GROUND_AFTER_FLIGHT_KNOWN_AIRPORT_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), destinationAirportDto.getName(), destinationAirportDto.getIata(), destinationGeonameCityDto.getName(), destinationGeonameCityDto.getCountryName(), flight.getAirline(),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            departureAirportDto.getName(), departureAirportDto.getIata(), departureGeonameCityDto.getName(), departureGeonameCityDto.getCountryName(), flight.getCallsign(), flight.getId()
                                    ));
                                } else {
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_ON_GROUND_AFTER_FLIGHT_UNKNOWN_AIRPORT_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), destinationAirportDto.getName(), destinationAirportDto.getIata(), destinationGeonameCityDto.getName(), destinationGeonameCityDto.getCountryName(), flight.getAirline(),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            flight.getCallsign(), flight.getId()
                                    ));
                                }
                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                        unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                        unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                        unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                        flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        } else if (flight.getAltitude() < LOW_ALTITUDE_IN_M && flight.getVerticalSpeed() > 0 && !flightEntity.isTookOff()) {
                            try {
                                flightService.changeFlightTookOff(flightEntity, true);

                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());

                                if (flightEntity.getDepartureAirport() != null) {
                                    AirportDto airportDto = airportsUtils.getAirportByIataCode(flightEntity.getDepartureAirport());
                                    GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), airportDto.getCountry(), user.getBotLanguage().name());
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_TAKEOFF_FROM_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), airportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAirline(),
                                            unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            flight.getCallsign(), flight.getId()
                                    ));
                                } else {
                                    AirportDto airportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), CLOSE_TO_AIRPORT_RANGE_IN_KM);
                                    GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), airportDto.getCountry(), user.getBotLanguage().name());
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_TAKEOFF_NEAR_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), airportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAirline(),
                                            unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            flight.getCallsign(), flight.getId()
                                    ));
                                }

                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                        unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                        unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                        unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                        flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        } else if (flight.getAltitude() < LOW_ALTITUDE_IN_M && flight.getVerticalSpeed() < APPROACHING_VERTICAL_SPEED_IN_FPM && flightEntity.isTookOff() && !flightEntity.isLanding()) {
                            try {
                                flightService.changeFlightLanding(flightEntity, true);
                                AirportDto destinationAirportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), CLOSE_TO_AIRPORT_RANGE_IN_KM);
                                GeonameDto destinationGeonameCityDto = geonameService.getObject(destinationAirportDto.getCity(), destinationAirportDto.getCountry(), user.getBotLanguage().name());
                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());

                                if (flightEntity.getDepartureAirport() != null) {
                                    AirportDto departureAirportDto = airportsUtils.getAirportByIataCode(flightEntity.getDepartureAirport());
                                    GeonameDto departureGeonameCityDto = geonameService.getObject(departureAirportDto.getCity(), departureAirportDto.getCountry(), user.getBotLanguage().name());
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LANDING_KNOWN_AIRPORT_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), destinationAirportDto.getName(), destinationAirportDto.getIata(), destinationGeonameCityDto.getName(), destinationGeonameCityDto.getCountryName(), flight.getAirline(),
                                            unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            departureAirportDto.getName(), departureAirportDto.getIata(), departureGeonameCityDto.getName(), departureGeonameCityDto.getCountryName(), flight.getCallsign(), flight.getId()
                                    ));
                                } else {
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LANDING_UNKNOWN_AIRPORT_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), destinationAirportDto.getName(), destinationAirportDto.getIata(), destinationGeonameCityDto.getName(), destinationGeonameCityDto.getCountryName(), flight.getAirline(),
                                            unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            flight.getCallsign(), flight.getId()
                                    ));
                                }

                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = BaseBotMessage.getBaseBotMessage(user.getChatId());

                                if (flightEntity.getDepartureAirport() != null) {
                                    AirportDto departureAirportDto = airportsUtils.getAirportByIataCode(flightEntity.getDepartureAirport());
                                    GeonameDto departureGeonameCityDto = geonameService.getObject(departureAirportDto.getCity(), departureAirportDto.getCountry(), user.getBotLanguage().name());
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LANDING_NO_NEAR_AIRPORT_KNOWN_AIRPORT_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                            unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            departureAirportDto.getName(), departureAirportDto.getIata(), departureGeonameCityDto.getName(), departureGeonameCityDto.getCountryName(), flight.getCallsign(), flight.getId()
                                    ));
                                } else {
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LANDING_NO_NEAR_AIRPORT_UNKNOWN_AIRPORT_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), flight.getAirline(),
                                            unitsUtils.convertAltitudeToUserUnitsSystem(flight.getAltitude(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertSpeedToUserUnitsSystem(flight.getGroundSpeed(), user.getUnitsSystem(), user.getBotLanguage()),
                                            unitsUtils.convertDistanceToUserUnitsSystem(flight.getDistance(), user.getUnitsSystem(), user.getBotLanguage()),
                                            flight.getCallsign(), flight.getId()
                                    ));
                                }

                                botSender.sendMessage(message);
                            }
                        }
                    }
                }
            }
        }
        log.info("Schedule sending An-124 flights finished.");
    }
}
