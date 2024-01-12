package snappfood.ordersdelay.orders.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import snappfood.ordersdelay.orders.data.entities.enums.DelayReportStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Table
@Entity(name = "delay_reports")
public class DelayReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "processed")
    private boolean processed;

    @Column(name = "current_delivery_date")
    private LocalDateTime currentDeliveryDate;

    @Column(name = "new_delivery_date")
    private LocalDateTime newDeliveryDate;

    @Column(name = "agent_id")
    private Long agentId;

    @Column(name = "order_id")
    private Long orderId;
}
