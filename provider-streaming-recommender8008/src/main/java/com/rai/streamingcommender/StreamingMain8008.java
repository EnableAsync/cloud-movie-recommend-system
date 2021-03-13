package com.rai.streamingcommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StreamingMain8008 {
    public static void main(String[] args) {
        SpringApplication.run(StreamingMain8008.class, args);
    }
}
