package snappfood.ordersdelay.trips.services;

import org.springframework.stereotype.Service;
import snappfood.ordersdelay.trips.entities.TripEntity;
import snappfood.ordersdelay.trips.entities.enums.TripStatus;
import snappfood.ordersdelay.trips.repositories.TripRepository;

import java.util.Optional;

@Service
public class TripService {

    private final TripRepository repository;

    public TripService(TripRepository repository) {
        this.repository = repository;
    }

    public boolean isTripExistWithDeliveredStatus(long orderId) {
        Optional<TripEntity> trip = this.repository.findByOrderId(orderId);
        return trip.map(tripEntity -> tripEntity.getStatus() == TripStatus.DELIVERED).orElse(true);
    }
}
