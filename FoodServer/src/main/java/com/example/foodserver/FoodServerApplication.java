package com.example.foodserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "com.example.foodserver.Entity")
public class FoodServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodServerApplication.class, args);
    }
}