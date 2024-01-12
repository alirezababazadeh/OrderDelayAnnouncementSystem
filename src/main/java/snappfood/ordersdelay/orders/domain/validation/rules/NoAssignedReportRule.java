package snappfood.ordersdelay.orders.domain.validation.rules;

import snappfood.ordersdelay.core.validations.Rule;
import snappfood.ordersdelay.orders.data.repositories.DelayReportRepository;

public class NoAssignedReportRule implements Rule {

    private final DelayReportRepository reportRepository;
    private final long agentId;

    public NoAssignedReportRule(DelayReportRepository reportRepository, long agentId) {
        this.reportRepository = reportRepository;
        this.agentId = agentId;
    }

    @Override
    public boolean apply() {
        return reportRepository.findByAgentIdAndProcessed(this.agentId, false).isEmpty();
    }

    @Override
    public String getMessage() {
        return "You have an uncompleted report!";
    }
}
