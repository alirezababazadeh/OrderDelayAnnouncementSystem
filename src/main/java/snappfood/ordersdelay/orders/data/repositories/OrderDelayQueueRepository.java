package snappfood.ordersdelay.orders.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import snappfood.ordersdelay.orders.data.entities.OrderDelayQueue;

import java.util.Optional;

@Repository
public interface OrderDelayQueueRepository extends JpaRepository<OrderDelayQueue, Long> {
    @Query(value = "select u from order_delays_queue u order by u.id asc limit 1")
    Optional<OrderDelayQueue> findFirstByOrderById();
}
