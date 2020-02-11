package com.example.belot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BelotScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BelotScoreApplication.class, args);
    }

}
