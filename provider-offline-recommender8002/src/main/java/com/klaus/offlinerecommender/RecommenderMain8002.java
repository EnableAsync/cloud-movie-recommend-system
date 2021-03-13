package com.klaus.offlinerecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RecommenderMain8002 {
    // 鑫哥帅帅
    public static void main(String[] args) {
        SpringApplication.run(RecommenderMain8002.class, args);
    }
}
