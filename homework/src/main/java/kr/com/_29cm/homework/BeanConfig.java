package kr.com._29cm.homework;

import kr.com._29cm.homework.service.OrderService;
import kr.com._29cm.homework.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BeanConfig {
    @Bean
    public OrderApplication orderApplication() {
        return new OrderApplication();
    }
}

