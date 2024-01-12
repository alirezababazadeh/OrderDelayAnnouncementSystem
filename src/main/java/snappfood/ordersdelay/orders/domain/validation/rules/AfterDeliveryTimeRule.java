package snappfood.ordersdelay.orders.domain.validation.rules;

import snappfood.ordersdelay.core.validations.Rule;
import snappfood.ordersdelay.orders.data.entities.OrderEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AfterDeliveryTimeRule implements Rule {

    private final OrderEntity order;

    public AfterDeliveryTimeRule(OrderEntity order) {
        this.order = order;
    }

    @Override
    public boolean apply() {
        LocalDateTime orderDeliverAt = this.order.getCreatedAt().plusMinutes(this.order.getDeliveryTime());
        return orderDeliverAt.isBefore(LocalDateTime.now());
    }

    @Override
    public String getMessage() {
        return "You are not allowed to announce a delay report for this order until " +
                this.order
                        .getCreatedAt()
                        .plusMinutes(this.order.getDeliveryTime())
                        .format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
