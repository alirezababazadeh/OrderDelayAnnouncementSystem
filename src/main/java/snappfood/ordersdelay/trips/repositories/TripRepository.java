package snappfood.ordersdelay.trips.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import snappfood.ordersdelay.trips.entities.TripEntity;
import snappfood.ordersdelay.trips.entities.enums.TripStatus;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {
    Optional<TripEntity> findByOrderId(Long orderId);
}
