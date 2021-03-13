package com.rai.streamingcommender.utils;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */
@Configuration
public class Configure {
    @Value("${spring.data.mongodb.uri}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private int mongoPort;

    @Value("${spring.data.redis.host}")
    private String redisHost;


    @Bean(name = "mongoClient")
    public MongoClient getMongoClient(){
        return new MongoClient( mongoHost , mongoPort );
    }

    @Bean(name = "jedis")
    public Jedis getRedisClient() {
        return new Jedis(redisHost);
    }
}
