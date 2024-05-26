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
import project.vilsoncake.telegrambot.constant.TimeConst;
import project.vilsoncake.telegrambot.dto.An124FlightDataDto;
import project.vilsoncake.telegrambot.dto.An124FlightsDto;
import project.vilsoncake.telegrambot.dto.FlightDataDto;
import project.vilsoncake.telegrambot.dto.FlightsDto;
import project.vilsoncake.telegrambot.entity.FlightEntity;
import project.vilsoncake.telegrambot.entity.UserEntity;
import project.vilsoncake.telegrambot.service.FlightService;
import project.vilsoncake.telegrambot.service.UserService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ScheduleSender {

    private final UserService userService;
    private final AbsSender absSender;
    private final FlightService flightService;
    private final WebClient webClient;

    @Transactional
    @Scheduled(fixedDelay = TimeConst.FLIGHT_CHECK_DELAY_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void checkNewFlightAndSendsThem() throws TelegramApiException {
        List<UserEntity> users = userService.findAllUsers();

        for (UserEntity user : users) {
            // An-124 check
            An124FlightsDto an124FlightsDto = webClient.get()
                    .uri(String.format("/aircraft/%s/%s", MessageConst.AN_124_CODE, user.getAirport()))
                    .retrieve()
                    .bodyToMono(An124FlightsDto.class)
                    .block();

            for (An124FlightDataDto flight : an124FlightsDto.getFlights()) {
                if (!flightService.existsByUserAndFlightId(user, flight.getId())) {
                    FlightEntity flightEntity = new FlightEntity(flight.getId(), user);
                    flightService.addFlightToUser(flightEntity);

                    SendMessage message = new SendMessage();
                    message.setChatId(user.getChatId());
                    message.setText(String.format(MessageConst.AN_124_FLIGHT_TEXT,
                            flight.getAltitude(), flight.getGroundSpeed(), flight.getDistance(), flight.getCallsign(), flight.getId()
                    ));

                    absSender.execute(message);
                }
            }

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

                    try {
                        absSender.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

}
