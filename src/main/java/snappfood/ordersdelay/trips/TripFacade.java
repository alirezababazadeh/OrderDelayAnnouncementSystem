package snappfood.ordersdelay.trips;

import org.springframework.stereotype.Component;
import snappfood.ordersdelay.trips.services.TripService;

@Component
public class TripFacade {

    private final TripService service;

    public TripFacade(TripService service) {
        this.service = service;
    }

    public boolean anyDeliveredTripOrNoTrip(long orderId) {
        return this.service.isTripExistWithDeliveredStatus(orderId);
    }
}
