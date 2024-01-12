package snappfood.ordersdelay.core.rest;

import org.springframework.stereotype.Component;

@Component
public interface RestConsumer<T> {
    T execute();
    T fallback(Throwable t);
}
