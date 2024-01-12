package snappfood.ordersdelay.orders.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snappfood.ordersdelay.orders.data.entities.DelayReportEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DelayReportRepository extends JpaRepository<DelayReportEntity, Long> {
    Optional<DelayReportEntity> findByOrderIdAndProcessed(Long orderId, boolean processed);
    Optional<DelayReportEntity> findByOrderIdAndProcessedAndAgentIdIsNull(Long orderId, boolean processed);
    Optional<DelayReportEntity> findByAgentIdAndProcessed(Long agentId, boolean processed);
}
