package project.vilsoncake.botadminpanel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.vilsoncake.botadminpanel.entity.enumerated.BotLanguage;
import project.vilsoncake.botadminpanel.entity.enumerated.BotMode;
import project.vilsoncake.botadminpanel.entity.enumerated.UserState;

import java.util.Date;
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

    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();
}