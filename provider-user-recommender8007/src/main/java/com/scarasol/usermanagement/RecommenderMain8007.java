package com.scarasol.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RecommenderMain8007 {
    public static void main(String[]args){
        SpringApplication.run(RecommenderMain8007.class,args);
    }
}
