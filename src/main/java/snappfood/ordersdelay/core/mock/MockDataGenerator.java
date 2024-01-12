package snappfood.ordersdelay.core.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snappfood.ordersdelay.orders.data.entities.OrderEntity;
import snappfood.ordersdelay.orders.data.repositories.OrderRepository;
import snappfood.ordersdelay.trips.entities.TripEntity;
import snappfood.ordersdelay.trips.entities.enums.TripStatus;
import snappfood.ordersdelay.trips.repositories.TripRepository;
import snappfood.ordersdelay.vendors.entities.VendorEntity;
import snappfood.ordersdelay.vendors.repositories.VendorRepository;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MockDataGenerator {

    private final OrderRepository orderRepository;
    private final TripRepository tripRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public MockDataGenerator(
            OrderRepository orderRepository,
            TripRepository tripRepository,
            VendorRepository vendorRepository
    ) {
        this.orderRepository = orderRepository;
        this.tripRepository = tripRepository;
        this.vendorRepository = vendorRepository;
    }

    public void generate() {
        for (int i = 0; i < 10; i++) {
            VendorEntity vendor = new VendorEntity();
            vendor = vendorRepository.save(vendor);
            for (int j = 0; j < 50; j++) {
                OrderEntity order = new OrderEntity();
                order.setCreatedAt(LocalDateTime.now());
                order.setDeliveryTime(new Random().nextInt(10));
                order.setVendorId(vendor.getId());
                order = orderRepository.save(order);
                if (order.getDeliveryTime() % 2 == 0) {
                    TripEntity trip = new TripEntity();
                    if (j % 2 == 0) {
                        trip.setStatus(TripStatus.ASSIGNED);
                    } else {
                        trip.setStatus(TripStatus.DELIVERED);
                    }
                    trip.setOrderId(order.getId());
                    tripRepository.save(trip);
                }
            }
        }
    }
}
