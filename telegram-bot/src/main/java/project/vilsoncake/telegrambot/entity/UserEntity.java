package project.vilsoncake.telegrambot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.telegrambot.entity.enumerated.UserState;

import java.util.UUID;

@Entity
@Table(name = "user_")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "chat_id", unique = true)
    private Long chatId;

    @Column(name = "airport")
    private String airport;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private UserState state;

    public UserEntity(String username, Long chatId, UserState state) {
        this.username = username;
        this.chatId = chatId;
        this.state = state;
    }
}
