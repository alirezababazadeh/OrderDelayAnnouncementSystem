package snappfood.ordersdelay.trips.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import snappfood.ordersdelay.trips.entities.enums.TripStatus;

@Getter
@Setter
@Table
@Entity(name = "trips")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @Column(name = "order_id")
    private Long orderId;
}
