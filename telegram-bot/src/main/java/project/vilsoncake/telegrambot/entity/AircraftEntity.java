package project.vilsoncake.telegrambot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.vilsoncake.telegrambot.entity.enumerated.AircraftFamily;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "aircraft")
@Getter
@Setter
@NoArgsConstructor
public class AircraftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "family", nullable = false)
    private AircraftFamily family;

    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public AircraftEntity(AircraftFamily family, UserEntity user) {
        this.family = family;
        this.user = user;
    }
}
