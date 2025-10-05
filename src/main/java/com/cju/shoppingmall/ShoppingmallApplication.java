package com.cju.shoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShoppingmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingmallApplication.class, args);
    }

}
