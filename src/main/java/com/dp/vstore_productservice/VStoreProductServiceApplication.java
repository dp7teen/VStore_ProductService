package com.dp.vstore_productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VStoreProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VStoreProductServiceApplication.class, args);
    }

}
