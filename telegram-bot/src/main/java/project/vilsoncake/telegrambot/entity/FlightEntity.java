package project.vilsoncake.telegrambot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "flight")
@Data
@NoArgsConstructor
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "flight_id", updatable = false, nullable = false)
    private String flightId;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "active")
    private boolean active = false;

    @Column(name = "created_at", updatable = false)
    private Date date = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public FlightEntity(String flightId, UserEntity user) {
        this.flightId = flightId;
        this.user = user;
    }
}
