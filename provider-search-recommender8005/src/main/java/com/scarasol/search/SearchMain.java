package com.scarasol.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SearchMain {
    public static void main(String[]args){
        SpringApplication.run(SearchMain.class,args);
    }
}
