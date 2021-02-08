package com.epam.webserviceproducing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebServiceProducingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceProducingApplication.class, args);
    }

}
