package snappfood.ordersdelay.orders.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity(name = "order_delays_queue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDelayQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;
}
