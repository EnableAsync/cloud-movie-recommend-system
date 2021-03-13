package com.klaus.consumer;

import com.klaus.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RibbonClient(name = "cloud-office-recommender-service", configuration = MySelfRule.class)
public class Recommender80 {
    public static void main(String[] args) {
        SpringApplication.run(Recommender80.class, args);
    }
}
