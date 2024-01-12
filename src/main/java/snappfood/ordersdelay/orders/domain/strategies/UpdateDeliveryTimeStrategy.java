package snappfood.ordersdelay.orders.domain.strategies;

import jakarta.transaction.Transactional;
import snappfood.ordersdelay.core.rest.RestConsumer;
import snappfood.ordersdelay.core.strategies.Strategy;
import snappfood.ordersdelay.orders.data.entities.DelayReportEntity;
import snappfood.ordersdelay.orders.data.entities.OrderEntity;
import snappfood.ordersdelay.orders.data.repositories.DelayReportRepository;
import snappfood.ordersdelay.orders.data.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateDeliveryTimeStrategy implements Strategy {

    private final RestConsumer<Object> deliveryTimeConsumer;
    private final OrderEntity order;
    private final DelayReportRepository delayReportRepository;
    private final OrderRepository orderRepository;

    private LocalDateTime result;

    public UpdateDeliveryTimeStrategy(
            RestConsumer<Object> deliveryTimeConsumer,
            OrderEntity order,
            DelayReportRepository delayReportRepository,
            OrderRepository orderRepository
    ) {
        this.deliveryTimeConsumer = deliveryTimeConsumer;
        this.order = order;
        this.delayReportRepository = delayReportRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Strategy apply() {
        int newDeliveryTime = (int) deliveryTimeConsumer.execute();

        DelayReportEntity report = new DelayReportEntity();
        report.setCurrentDeliveryDate(order.getCreatedAt().plusMinutes(order.getDeliveryTime()));

        order.setDeliveryTime(newDeliveryTime);
        orderRepository.save(order);

        report.setProcessed(true);
        report.setNewDeliveryDate(order.getCreatedAt().plusMinutes(order.getDeliveryTime()));
        report.setOrderId(order.getId());
        delayReportRepository.save(report);

        this.result = order.getCreatedAt().plusMinutes(order.getDeliveryTime());
        return this;
    }

    @Override
    public String getResult() {
        return "The new delivery date for the order is: " + this.result.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
