package snappfood.ordersdelay.orders.data.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<?> reportVendorsDelay() {
        return entityManager.createNativeQuery(
                "select o.vendor_id as vendor_id, " +
                        "o.id as order_id, " +
                        "timestampdiff(minute, min(dr.current_delivery_date), max(dr.new_delivery_date)) as delay " +
                        "from orders o inner join delay_reports dr on o.id = dr.order_id " +
                        "where timestampdiff(minute, o.created_at, now()) < 7 * 24 * 60 " +
                        "group by o.vendor_id, o.id " +
                        "order by delay desc;"
        ).getResultList();
    }
}
