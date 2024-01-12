package snappfood.ordersdelay.orders.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table
@Entity(name = "orders")

public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_time")
    private Integer deliveryTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "vendor_id")
    private Long vendorId;
}
