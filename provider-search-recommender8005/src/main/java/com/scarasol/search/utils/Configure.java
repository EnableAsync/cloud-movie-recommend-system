package com.scarasol.search.utils;

import com.mongodb.MongoClient;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;


import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class Configure {

//    @Value("${spring.data.mongodb.uri}")
//    private String mongoHost;
//    @Value("${spring.data.mongodb.port}")
//    private int mongoPort;
//
//    @Value("${spring.data.elasticsearch.esClusterName}")
//    private String esClusterName;
//
//    @Value("${spring.data.elasticsearch.esHost}")
//    private String esHost;
//
//    @Value("${spring.data.elasticsearch.esPort}")
//    private int esPort;


//    @Bean(name = "mongoClient")
//    public MongoClient getMongoClient(){
//        return new MongoClient( mongoHost , mongoPort );
//    }
//
//    @Bean(name = "transportClient")
//    public TransportClient getTransportClient() throws UnknownHostException {
//        Settings settings = Settings.builder().put("cluster.name",esClusterName).build();
//        TransportClient esClient = new PreBuiltTransportClient(settings);
//        esClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), esPort));
//        return esClient;
//    }


}
