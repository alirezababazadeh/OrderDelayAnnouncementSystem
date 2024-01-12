package snappfood.ordersdelay.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InjectionConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
