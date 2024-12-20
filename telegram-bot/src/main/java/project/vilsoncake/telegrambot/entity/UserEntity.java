package project.vilsoncake.telegrambot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.vilsoncake.telegrambot.entity.enumerated.BotLanguage;
import project.vilsoncake.telegrambot.entity.enumerated.BotMode;
import project.vilsoncake.telegrambot.entity.enumerated.UnitsSystem;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "chat_id", unique = true)
    private Long chatId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "email_code")
    private int emailCode = 0;

    @Column(name = "airport")
    private String airport;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private UserState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "bot_mode", nullable = false)
    private BotMode botMode = BotMode.ALL;

    @Enumerated(EnumType.STRING)
    @Column(name = "bot_language")
    private BotLanguage botLanguage = BotLanguage.ENG;

    @Enumerated(EnumType.STRING)
    @Column(name = "units_system")
    private UnitsSystem unitsSystem = UnitsSystem.METRIC;

    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<FlightEntity> flights;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<AircraftEntity> aircraft;

    public UserEntity(String username, Long chatId, UserState state) {
        this.username = username;
        this.chatId = chatId;
        this.state = state;
    }
}
