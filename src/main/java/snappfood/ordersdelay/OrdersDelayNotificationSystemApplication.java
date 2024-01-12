package snappfood.ordersdelay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class OrdersDelayNotificationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersDelayNotificationSystemApplication.class, args);
    }
}
