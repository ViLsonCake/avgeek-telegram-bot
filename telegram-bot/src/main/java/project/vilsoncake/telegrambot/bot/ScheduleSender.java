package project.vilsoncake.telegrambot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.vilsoncake.telegrambot.constant.MessageConst;
import project.vilsoncake.telegrambot.constant.NumberConst;
import project.vilsoncake.telegrambot.dto.An124FlightDataDto;
import project.vilsoncake.telegrambot.dto.An124FlightsDto;
import project.vilsoncake.telegrambot.dto.FlightDataDto;
import project.vilsoncake.telegrambot.dto.FlightsDto;
import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.service.FlightService;
import project.vilsoncake.telegrambot.service.UserService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static project.vilsoncake.telegrambot.constant.NumberConst.*;

@Component
@RequiredArgsConstructor
public class ScheduleSender {

    private final UserService userService;
    private final AbsSender absSender;
    private final FlightService flightService;
    private final WebClient webClient;

    @Transactional
    @Scheduled(fixedDelay = NumberConst.FLIGHT_CHECK_DELAY_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void checkNewFlightAndSendsThem() throws TelegramApiException {
        List<UserEntity> users = userService.findAllUsers();

        for (UserEntity user : users) {

            if (user.getBotMode().equals(BotMode.ALL) || user.getBotMode().equals(BotMode.ONLY_AN_124_FLIGHTS)) {
                // An-124 check
                An124FlightsDto an124FlightsDto = webClient.get()
                        .uri(String.format("/aircraft/%s/%s", MessageConst.AN_124_CODE, user.getAirport()))
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
                            message.setText(String.format(MessageConst.AN_124_IN_YOUR_AIRPORT_NOW_TEXT,
                                    flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            absSender.execute(message);
                        } else if (flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM) {
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setText(String.format(MessageConst.AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT,
                                    flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            absSender.execute(message);
                        } else {
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setText(String.format(MessageConst.AN_124_FLIGHT_TEXT,
                                    flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            absSender.execute(message);
                        }

                    } else {
                        FlightEntity flightEntity = flightService.findByUserAndFlightId(user, flight.getId());
                        if (flight.getDistance() < flightEntity.getDistance() && flight.getDistance() < FLIGHT_IN_AIRPORT_DISTANCE_IN_KM && flight.getAltitude() < FLIGHT_IN_AIRPORT_ALTITUDE_IN_M && flightEntity.isActive()) {
                            flightService.changeFlightDistance(flightEntity, flight.getDistance());
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setText(String.format(MessageConst.AN_124_IN_YOUR_AIRPORT_NOW_TEXT,
                                    flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            absSender.execute(message);
                        } else if (flight.getDistance() < flightEntity.getDistance() && flight.getDistance() < NOTIFY_AGAIN_DISTANCE_IN_KM && flightEntity.isActive()) {
                            flightService.changeFlightDistance(flightEntity, flight.getDistance());
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setText(String.format(MessageConst.AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT,
                                    flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            absSender.execute(message);
                        } else if (flight.getDistance() < flightEntity.getDistance() && flight.getDistance() < FLIGHT_CLOSE_TO_AIRPORT_DISTANCE_IN_KM && !flightEntity.isActive()) {
                            flightService.changeFlightActive(flightEntity, true);
                            flightService.changeFlightDistance(flightEntity, flight.getDistance());
                            SendMessage message = new SendMessage();
                            message.setChatId(user.getChatId());
                            message.setText(String.format(MessageConst.AN_124_NEARLY_CLOSE_TO_YOUR_AIRPORT_TEXT,
                                    flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                            ));
                            absSender.execute(message);
                        }
                    }
                }
            }
            if (user.getBotMode().equals(BotMode.ALL) || user.getBotMode().equals(BotMode.ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS)) {

                // Wide-body aircraft check
                FlightsDto flightsDto = webClient.get()
                        .uri("/airport/flights/" + user.getAirport())
                        .retrieve()
                        .bodyToMono(FlightsDto.class)
                        .block();

                for (FlightDataDto flight : flightsDto.getFlights()) {
                    if (!flightService.existsByUserAndFlightId(user, flight.getId())) {
                        FlightEntity flightEntity = new FlightEntity(flight.getId(), user);
                        flightService.addFlightToUser(flightEntity);

                        SendMessage message = new SendMessage();
                        message.setChatId(user.getChatId());
                        message.setText(String.format(MessageConst.FLIGHT_TEXT,
                                flight.getAirport(), flight.getCode(), flight.getAirlineName(), user.getAirport()
                        ));

                        absSender.execute(message);
                    } else if (flightService.existsByUserAndFlightId(user, flight.getId()) && flight.isLive() && !flightService.findByUserAndFlightId(user, flight.getId()).isActive()) {
                        flightService.changeFlightActive(flightService.findByUserAndFlightId(user, flight.getId()), true);
                        SendMessage message = new SendMessage();
                        message.setChatId(user.getChatId());
                        message.setText(String.format(MessageConst.LIVE_FLIGHT_TEXT, flight.getAirport(), flight.getCode(), flight.getAirlineName(), flight.getCallsign(), flight.getId()));

                        absSender.execute(message);
                    }
                }
            }
        }
    }

}
