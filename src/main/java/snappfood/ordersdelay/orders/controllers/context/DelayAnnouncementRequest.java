package snappfood.ordersdelay.orders.controllers.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DelayAnnouncementRequest implements Serializable {

    private long orderId;
}
