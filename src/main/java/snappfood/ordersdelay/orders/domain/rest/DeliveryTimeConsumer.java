package snappfood.ordersdelay.orders.domain.rest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import snappfood.ordersdelay.core.rest.RestConsumer;

@Component
@Qualifier("DeliveryTimeConsumer")
public class DeliveryTimeConsumer implements RestConsumer<Object> {

    private static final Integer DEFAULT_NEW_DELIVERY_TIME = 200;

    private final RestTemplate restTemplate;

    public DeliveryTimeConsumer(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(
        fallbackMethod = "fallback",
        commandProperties = { 
            @HystrixProperty(
                 name = "execution.isolation.thread.timeoutInMilliseconds", 
                 value="5000"
            )
        }
    )
    public Object execute() {
        String url = "https://run.mocky.io/v3/122c2796-5df4-461c-ab75-87c1192b17f7";
        return restTemplate.getForObject(url, Integer.class);
    }

    public Object fallback(Throwable t) {
        return DEFAULT_NEW_DELIVERY_TIME;
    }
}