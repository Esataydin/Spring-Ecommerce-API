package com.esataydin.springecommerceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.esataydin")
@EnableJpaRepositories(basePackages = "com.esataydin")
@EntityScan(basePackages = "com.esataydin")
public class SpringEcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEcommerceApiApplication.class, args);
    }

}
