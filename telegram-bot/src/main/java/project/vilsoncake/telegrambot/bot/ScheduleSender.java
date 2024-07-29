package project.vilsoncake.telegrambot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.telegrambot.constant.BotMessageEngConst;
import project.vilsoncake.telegrambot.constant.NumberConst;
import project.vilsoncake.telegrambot.dto.*;
import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.exception.AirportNotFoundException;
import project.vilsoncake.telegrambot.service.FlightService;
import project.vilsoncake.telegrambot.service.GeonameService;
import project.vilsoncake.telegrambot.service.MailService;
import project.vilsoncake.telegrambot.service.UserService;
import project.vilsoncake.telegrambot.utils.AirportsUtils;
import project.vilsoncake.telegrambot.utils.BotMessageUtils;
import project.vilsoncake.telegrambot.utils.MailMessageUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static project.vilsoncake.telegrambot.constant.CommandConst.MARKDOWN_PARSE_MODE;
import static project.vilsoncake.telegrambot.constant.NumberConst.*;
import static project.vilsoncake.telegrambot.entity.enumerated.BotMessageTemplate.*;
import static project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate.AN_124_IN_AIRPORT_MESSAGE_SUBJECT;
import static project.vilsoncake.telegrambot.entity.enumerated.MailMessageTemplate.AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT;

@Component
@RequiredArgsConstructor
public class ScheduleSender {

    private final UserService userService;
    private final FlightService flightService;
    private final MailService mailService;
    private final GeonameService geonameService;
    private final BotMessageUtils botMessageUtils;
    private final MailMessageUtils mailMessageUtils;
    private final AirportsUtils airportsUtils;
    private final WebClient apiWebClient;
    private final BotSender botSender;

    @Transactional
    @Scheduled(fixedDelay = NumberConst.FLIGHT_CHECK_DELAY_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void checkNewFlightAndSendsThem() {
        List<UserEntity> users = userService.findAllUsers();

        for (UserEntity user : users) {

            if (user.getBotMode().equals(BotMode.ALL) || user.getBotMode().equals(BotMode.ONLY_AN_124_FLIGHTS)) {
                // An-124 check
                An124FlightsDto an124FlightsDto = apiWebClient.get()
                        .uri(String.format("/aircraft/%s/%s", BotMessageEngConst.AN_124_CODE, user.getAirport()))
                        .retrieve()
                        .bodyToMono(An124FlightsDto.class)
                        .block();

                for (An124FlightDataDto flight : an124FlightsDto.getFlights()) {
                    if (!flightService.existsByUserAndFlightId(user, flight.getId())) {
                        FlightEntity flightEntity = new FlightEntity(flight.getId(), user);
                        flightEntity.setDistance(flight.getDistance());
                        flightService.addFlightToUser(flightEntity);

                        if (flight.getDistance() < FLIGHT_IN_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() < FLIGHT_IN_AIRPORT_ALTITUDE_IN_M) {
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setParseMode(MARKDOWN_PARSE_MODE);
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_IN_YOUR_AIRPORT_NOW_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() < FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M) {
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setParseMode(MARKDOWN_PARSE_MODE);
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LIKELY_TO_LAND_AIRPORT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() > FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M) {
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setParseMode(MARKDOWN_PARSE_MODE);
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getAltitude() == ON_GROUND_ALTITUDE) {
                            try {
                                AirportDto airportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), ON_GROUND_RADIUS);
                                GeonameDto geonameAirportDto = geonameService.getObject(airportDto.getIcao(), user.getBotLanguage().name(), true);
                                GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), user.getBotLanguage().name(), false);
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_ON_GROUND_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), geonameAirportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        } else if (flight.getAltitude() < LOW_ALTITUDE_IN_M) {
                            try {
                                AirportDto airportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), CLOSE_TO_AIRPORT_RANGE_IN_KM);
                                GeonameDto geonameAirportDto = geonameService.getObject(airportDto.getIcao(), user.getBotLanguage().name(), true);
                                GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), user.getBotLanguage().name(), false);
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());

                                if (flight.getVerticalSpeed() > 0) {
                                    flightService.changeFlightTookOff(flightEntity, true);
                                    message.setParseMode(MARKDOWN_PARSE_MODE);
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_TAKEOFF_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), geonameAirportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                    ));
                                } else if (flight.getVerticalSpeed() < 0) {
                                    flightService.changeFlightLanding(flightEntity, true);
                                    message.setParseMode(MARKDOWN_PARSE_MODE);
                                    message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LANDING_TEXT, user.getBotLanguage()),
                                            flight.getId().substring(flight.getId().length() - 4), geonameAirportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                    ));
                                }

                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        } else {
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setParseMode(MARKDOWN_PARSE_MODE);
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        }

                    } else {
                        FlightEntity flightEntity = flightService.findByUserAndFlightId(user, flight.getId());
                        if (flight.getDistance() < flightEntity.getDistance() && flight.getDistance() < FLIGHT_IN_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() < FLIGHT_IN_AIRPORT_ALTITUDE_IN_M && flightEntity.isActive()) {
                            flightService.changeFlightDistance(flightEntity, flight.getDistance());
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setParseMode(MARKDOWN_PARSE_MODE);
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_IN_YOUR_AIRPORT_NOW_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);

                            if (user.isEmailVerified()) {
                                mailService.sendMessage(
                                        user.getEmail(),
                                        mailMessageUtils.getMessageByLanguage(AN_124_IN_AIRPORT_MESSAGE_SUBJECT, user.getBotLanguage()),
                                        String.format(mailMessageUtils.getMessageByLanguage(AN_124_IN_YOUR_AIRPORT_MESSAGE_TEXT, user.getBotLanguage()), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId())
                                );
                            }

                        } else if (flight.getDistance() < flightEntity.getDistance() && flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() < FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M && !flightEntity.isActive()) {
                            flightService.changeFlightActive(flightEntity, true);
                            flightService.changeFlightDistance(flightEntity, flight.getDistance());
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setParseMode(MARKDOWN_PARSE_MODE);
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LIKELY_TO_LAND_AIRPORT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getDistance() < flightEntity.getDistance() || flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() > FLIGHT_MAYBE_IN_AIRPORT_ALTITUDE_IN_M) {
                            flightService.changeFlightDistance(flightEntity, flight.getDistance());
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setParseMode(MARKDOWN_PARSE_MODE);
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLYING_NEAR_YOUR_AIRPORT_TEXT, user.getBotLanguage()),
                                    flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            botSender.sendMessage(message);
                        } else if (flight.getAltitude() == ON_GROUND_ALTITUDE && flightEntity.isTookOff() && !flightEntity.isOnGround()) {
                            try {
                                flightService.changeFlightOnGround(flightEntity, true);
                                AirportDto airportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), ON_GROUND_RADIUS);
                                GeonameDto geonameAirportDto = geonameService.getObject(airportDto.getIcao(), user.getBotLanguage().name(), true);
                                GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), user.getBotLanguage().name(), false);
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_ON_GROUND_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), geonameAirportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        } else if (flight.getAltitude() < LOW_ALTITUDE_IN_M && flight.getVerticalSpeed() > 0 && !flightEntity.isTookOff()) {
                            try {
                                flightService.changeFlightTookOff(flightEntity, true);
                                AirportDto airportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), CLOSE_TO_AIRPORT_RANGE_IN_KM);
                                GeonameDto geonameAirportDto = geonameService.getObject(airportDto.getIcao(), user.getBotLanguage().name(), true);
                                GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), user.getBotLanguage().name(), false);
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_TAKEOFF_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), geonameAirportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));

                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        } else if (flight.getAltitude() < LOW_ALTITUDE_IN_M && flight.getVerticalSpeed() < 0 && flightEntity.isTookOff() && !flightEntity.isLanding()) {
                            try {
                                flightService.changeFlightLanding(flightEntity, true);
                                AirportDto airportDto = airportsUtils.findClosestAirportByCoordinates(flight.getLatitude(), flight.getLongitude(), CLOSE_TO_AIRPORT_RANGE_IN_KM);
                                GeonameDto geonameAirportDto = geonameService.getObject(airportDto.getIcao(), user.getBotLanguage().name(), true);
                                GeonameDto geonameCityDto = geonameService.getObject(airportDto.getCity(), user.getBotLanguage().name(), false);
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_LANDING_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), geonameAirportDto.getName(), airportDto.getIata(), geonameCityDto.getName(), geonameCityDto.getCountryName(), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));

                                botSender.sendMessage(message);
                            } catch (AirportNotFoundException e) {
                                SendMessage message = new SendMessage();
                                message.setChatId(user.getChatId());
                                message.setParseMode(MARKDOWN_PARSE_MODE);
                                message.setText(String.format(botMessageUtils.getMessageByLanguage(AN_124_FLIGHT_TEXT, user.getBotLanguage()),
                                        flight.getId().substring(flight.getId().length() - 4), flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                                ));
                                botSender.sendMessage(message);
                            }
                        }
                    }
                }
            }
            if (user.getBotMode().equals(BotMode.ALL) || user.getBotMode().equals(BotMode.ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS)) {

                // Wide-body aircraft check
                FlightsDto flightsDto = apiWebClient.get()
                        .uri("/airport/flights/" + user.getAirport())
                        .retrieve()
                        .bodyToMono(FlightsDto.class)
                        .block();

                for (FlightDataDto flight : flightsDto.getFlights()) {
                    if (!flightService.existsByUserAndFlightId(user, flight.getId())) {
                        GeonameDto geonameAirportDto = geonameService.getObject(flight.getIcao(), user.getBotLanguage().name(), true);

                        FlightEntity flightEntity = new FlightEntity(flight.getId(), user);
                        flightEntity.setRegistration(flight.getRegistration());
                        flightService.addFlightToUser(flightEntity);

                        SendMessage message = new SendMessage();
                        message.setChatId(user.getChatId());
                        message.setParseMode(MARKDOWN_PARSE_MODE);

                        if (flight.getRegistration() == null || flight.getRegistration().isBlank()) {
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(FLIGHT_WITHOUT_REGISTRATION_TEXT, user.getBotLanguage()),
                                    geonameAirportDto.getName(), flight.getIata(), geonameAirportDto.getCountryName(), flight.getAircraft(), flight.getAirlineName(), user.getAirport()
                            ));
                        } else {
                            message.setText(String.format(botMessageUtils.getMessageByLanguage(FLIGHT_TEXT, user.getBotLanguage()),
                                    flight.getRegistration(), geonameAirportDto.getName(), flight.getIata(), geonameAirportDto.getCountryName(), flight.getAircraft(), flight.getAirlineName(), flight.getRegistration(), user.getAirport()
                            ));
                        }

                        botSender.sendMessage(message);
                    } else if (flightService.existsByUserAndFlightId(user, flight.getId()) && flight.isLive() && !flightService.findByUserAndFlightId(user, flight.getId()).isActive()) {
                        GeonameDto geonameAirportDto = geonameService.getObject(flight.getIcao(), user.getBotLanguage().name(), true);

                        flightService.changeFlightActive(flightService.findByUserAndFlightId(user, flight.getId()), true);

                        SendMessage message = new SendMessage();
                        message.setChatId(user.getChatId());
                        message.setParseMode(MARKDOWN_PARSE_MODE);
                        message.setText(String.format(botMessageUtils.getMessageByLanguage(LIVE_FLIGHT_TEXT, user.getBotLanguage()),
                                flight.getRegistration(), geonameAirportDto.getName(), flight.getIata(), geonameAirportDto.getCountryName(), flight.getAircraft(), flight.getAirlineName(), flight.getRegistration(), flight.getCallsign(), flight.getId()
                        ));

                        botSender.sendMessage(message);
                    }
                }
            }
        }
    }

}
