package com.klaus.offlinerecommender.utils;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configure {

    private String mongoHost = "192.168.40.137";
    private int mongoPort = 27017;


    @Bean(name = "mongoClient")
    public MongoClient getMongoClient(){
        return new MongoClient( mongoHost , mongoPort );
    }


}
