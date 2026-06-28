package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class AsychronousBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsychronousBackendApplication.class, args);
    }

}
