package project.vilsoncake.telegrambot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "flight")
@Getter
@Setter
@NoArgsConstructor
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "flight_id", updatable = false, nullable = false)
    private String flightId;

    @Column(name = "registration")
    private String registration;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "active")
    private boolean active = false;

    @Column(name = "took_off")
    private boolean tookOff = false;

    @Column(name = "landing")
    private boolean landing = false;

    @Column(name = "on_ground")
    private boolean onGround = false;

    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public FlightEntity(String flightId, UserEntity user) {
        this.flightId = flightId;
        this.user = user;
    }
}
