package project.vilsoncake.botadminpanel.dto;

import lombok.Data;
import project.vilsoncake.botadminpanel.entity.enumerated.BotLanguage;
import project.vilsoncake.botadminpanel.entity.enumerated.BotMode;
import project.vilsoncake.botadminpanel.entity.enumerated.UnitsSystem;
import project.vilsoncake.botadminpanel.entity.enumerated.UserState;
import project.vilsoncake.botadminpanel.entity.jpa.UserEntity;
import project.vilsoncake.botadminpanel.utils.LocalApplicationDate;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private Long chatId;
    private String email;
    private boolean emailVerified;
    private int emailCode;
    private String airport;
    private UserState state;
    private String botMode;
    private BotLanguage botLanguage;
    private UnitsSystem unitsSystem;
    private String createdAt;

    public static UserDto fromEntity(UserEntity userEntity, String timezone) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        userDto.setChatId(userEntity.getChatId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setEmailVerified(userEntity.isEmailVerified());
        userDto.setEmailCode(userEntity.getEmailCode());
        userDto.setState(userEntity.getState());
        userDto.setBotMode(reformatBotMode(userEntity.getBotMode()));
        userDto.setBotLanguage(userEntity.getBotLanguage());
        userDto.setUnitsSystem(userEntity.getUnitsSystem());
        userDto.setCreatedAt(new LocalApplicationDate(userEntity.getCreatedAt(), timezone).getTime());

        if (userEntity.getAirport() != null) {
            userDto.setAirport(userEntity.getAirport().toUpperCase());
        } else {
            userDto.setAirport("nil");
        }

        return userDto;
    }

    private static String reformatBotMode(BotMode mode) {
        return switch (mode) {
            case ALL -> "All";
            case ONLY_AN_124_FLIGHTS -> "Only An-124";
            case ONLY_WIDE_BODY_AIRCRAFT_FLIGHTS -> "Only wide-body";
            case MUTE -> "Mute";
        };
    }
}
