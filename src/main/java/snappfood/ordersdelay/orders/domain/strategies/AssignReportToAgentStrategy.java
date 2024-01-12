package snappfood.ordersdelay.orders.domain.strategies;

import jakarta.transaction.Transactional;
import snappfood.ordersdelay.core.strategies.Strategy;
import snappfood.ordersdelay.orders.data.entities.DelayReportEntity;
import snappfood.ordersdelay.orders.data.entities.OrderDelayQueue;
import snappfood.ordersdelay.orders.data.repositories.DelayReportRepository;
import snappfood.ordersdelay.orders.data.repositories.OrderDelayQueueRepository;

import java.util.Optional;

public class AssignReportToAgentStrategy implements Strategy {

    private final DelayReportRepository reportRepository;
    private final OrderDelayQueueRepository queueRepository;
    private final long agentId;

    private String result;

    public AssignReportToAgentStrategy(
            DelayReportRepository reportRepository,
            OrderDelayQueueRepository queueRepository,
            long agentId
    ) {
        this.reportRepository = reportRepository;
        this.queueRepository = queueRepository;
        this.agentId = agentId;
    }

    @Transactional
    @Override
    public Strategy apply() {
        Optional<OrderDelayQueue> record = queueRepository.findFirstByOrderById();
        if (record.isPresent()) {
            Optional<DelayReportEntity> report = reportRepository.findByOrderIdAndProcessedAndAgentIdIsNull(
                    record.get().getOrderId(),
                    false
            );
            if (report.isPresent()) {
                DelayReportEntity entity = report.get();
                entity.setAgentId(this.agentId);
                reportRepository.save(entity);
                queueRepository.delete(record.get());
                this.result = "Record assigned successfully.";
            } else {
                queueRepository.delete(record.get());
                this.apply();
            }
        } else {
            this.result = "No record is already exist!";
        }
        return this;
    }

    @Override
    public String getResult() {
        return this.result;
    }
}
