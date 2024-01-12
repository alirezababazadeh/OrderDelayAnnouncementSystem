package snappfood.ordersdelay.orders.domain.strategies;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import snappfood.ordersdelay.core.strategies.Strategy;
import snappfood.ordersdelay.core.validations.Validator;
import snappfood.ordersdelay.orders.data.entities.DelayReportEntity;
import snappfood.ordersdelay.orders.data.entities.OrderDelayQueue;
import snappfood.ordersdelay.orders.data.entities.OrderEntity;
import snappfood.ordersdelay.orders.data.repositories.DelayReportRepository;
import snappfood.ordersdelay.orders.data.repositories.OrderDelayQueueRepository;
import snappfood.ordersdelay.orders.domain.exceptions.DelayReportException;
import snappfood.ordersdelay.orders.domain.validation.DelayAnnouncementValidator;
import snappfood.ordersdelay.orders.domain.validation.rules.SameDelayReportInQueueRule;

public class AddToQueueStrategy implements Strategy {

    private final OrderDelayQueueRepository queueRepository;
    private final OrderEntity order;
    private final DelayReportRepository delayReportRepository;

    public AddToQueueStrategy(
            OrderDelayQueueRepository queueRepository,
            OrderEntity order,
            DelayReportRepository delayReportRepository
    ) {
        this.queueRepository = queueRepository;
        this.order = order;
        this.delayReportRepository = delayReportRepository;
    }

    @SneakyThrows(DelayReportException.class)
    @Transactional
    @Override
    public Strategy apply() {
        new DelayAnnouncementValidator()
                .addRule(new SameDelayReportInQueueRule(delayReportRepository, order.getId()))
                .validate();
        OrderDelayQueue record = new OrderDelayQueue();
        record.setOrderId(order.getId());
        queueRepository.save(record);

        DelayReportEntity report = new DelayReportEntity();
        report.setProcessed(false);
        report.setCurrentDeliveryDate(order.getCreatedAt().plusMinutes(order.getDeliveryTime()));
        report.setOrderId(order.getId());
        delayReportRepository.save(report);

        return this;
    }

    @Override
    public String getResult() {
        return "The agent team are trying to solve your problem";
    }
}
