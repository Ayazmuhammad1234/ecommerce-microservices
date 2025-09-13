package com.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/v1")
public class userController {

    @Autowired
    private RestTemplate rest;

    @GetMapping("/get")
    @CircuitBreaker(name = "productServiceBreaker", fallbackMethod = "productFallback")
    public String getWelcome() {
        String msg = rest.getForObject("http://product-service/api/v1/get", String.class);
        return msg + " from product service";
    }

    // Fallback method called if product service is down
    public String productFallback(Throwable throwable) {
        return "Product service is down... Please try again later!";
    }
}
