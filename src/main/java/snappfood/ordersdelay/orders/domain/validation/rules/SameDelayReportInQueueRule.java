package snappfood.ordersdelay.orders.domain.validation.rules;

import snappfood.ordersdelay.core.validations.Rule;
import snappfood.ordersdelay.orders.data.repositories.DelayReportRepository;

public class SameDelayReportInQueueRule implements Rule {

    private final DelayReportRepository repository;
    private final long orderId;

    public SameDelayReportInQueueRule(DelayReportRepository repository, long orderId) {
        this.repository = repository;
        this.orderId = orderId;
    }

    @Override
    public boolean apply() {
        return repository.findByOrderIdAndProcessed(orderId, false).isEmpty();
    }

    @Override
    public String getMessage() {
        return "Report is already exist!";
    }
}
