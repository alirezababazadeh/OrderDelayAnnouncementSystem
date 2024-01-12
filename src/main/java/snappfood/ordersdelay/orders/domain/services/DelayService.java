package snappfood.ordersdelay.orders.domain.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import snappfood.ordersdelay.core.rest.RestConsumer;
import snappfood.ordersdelay.core.strategies.Strategy;
import snappfood.ordersdelay.orders.data.entities.OrderEntity;
import snappfood.ordersdelay.orders.data.repositories.CustomRepository;
import snappfood.ordersdelay.orders.data.repositories.OrderDelayQueueRepository;
import snappfood.ordersdelay.orders.domain.exceptions.DelayAssigmentException;
import snappfood.ordersdelay.orders.domain.exceptions.DelayReportException;
import snappfood.ordersdelay.orders.data.repositories.DelayReportRepository;
import snappfood.ordersdelay.orders.data.repositories.OrderRepository;
import snappfood.ordersdelay.orders.domain.strategies.AssignReportToAgentStrategy;
import snappfood.ordersdelay.orders.domain.validation.DelayAnnouncementValidator;
import snappfood.ordersdelay.orders.domain.validation.DelayAssignmentValidator;
import snappfood.ordersdelay.orders.domain.validation.rules.AfterDeliveryTimeRule;
import snappfood.ordersdelay.orders.domain.validation.rules.NoAssignedReportRule;
import snappfood.ordersdelay.orders.domain.strategies.AddToQueueStrategy;
import snappfood.ordersdelay.orders.domain.strategies.UpdateDeliveryTimeStrategy;
import snappfood.ordersdelay.trips.TripFacade;

import java.util.List;
import java.util.Optional;

@Service
public class DelayService {

    private final DelayReportRepository delayReportRepository;
    private final OrderRepository orderRepository;
    private final OrderDelayQueueRepository queueRepository;
    private final TripFacade tripFacade;
    private final RestConsumer<Object> deliveryTimeConsumer;
    private final CustomRepository customRepository;

    @Autowired
    public DelayService(
            DelayReportRepository delayReportRepository,
            OrderRepository orderRepository,
            OrderDelayQueueRepository queueRepository,
            TripFacade tripFacade,
            @Qualifier("DeliveryTimeConsumer") RestConsumer<Object> deliveryTimeConsumer,
            CustomRepository customRepository
    ) {
        this.delayReportRepository = delayReportRepository;
        this.orderRepository = orderRepository;
        this.queueRepository = queueRepository;
        this.tripFacade = tripFacade;
        this.deliveryTimeConsumer = deliveryTimeConsumer;
        this.customRepository = customRepository;
    }

    public String createOrderReport(long orderId) throws DelayReportException {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            new DelayAnnouncementValidator().addRule(new AfterDeliveryTimeRule(order.get())).validate();
            Strategy strategy = this.findDelayStrategy(order.get());
            return strategy.apply().getResult();
        }
        throw new DelayReportException("Order Not Found!!");
    }

    public String assignReportToAgent(long agentId) throws DelayAssigmentException {
        new DelayAssignmentValidator().addRule(new NoAssignedReportRule(delayReportRepository, agentId)).validate();
        return new AssignReportToAgentStrategy(
                delayReportRepository,
                queueRepository,
                agentId
        ).apply().getResult();
    }

    @Transactional
    public List<?> generateVendorDelayReport() {
        return customRepository.reportVendorsDelay();
    }

    private Strategy findDelayStrategy(OrderEntity order) {
        boolean deliveredTrip = tripFacade.anyDeliveredTripOrNoTrip(order.getId());
        if (deliveredTrip) {
            return new AddToQueueStrategy(
                    queueRepository,
                    order,
                    delayReportRepository
            );
        } else {
            return new UpdateDeliveryTimeStrategy(
                    deliveryTimeConsumer,
                    order,
                    delayReportRepository,
                    orderRepository
            );
        }
    }
}
