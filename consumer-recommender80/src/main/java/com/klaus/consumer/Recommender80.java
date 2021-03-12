package com.klaus.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Recommender80 {
    public static void main(String[]args){
        SpringApplication.run(Recommender80.class,args);
    }
}
