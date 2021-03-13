package com.rai.streamingcommender.service;


import com.rai.streamingcommender.utils.KafkaStream;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class KafkaService {
    @Resource
    private KafkaStream kafkaStream;

    public void sendMessage(String msg) {
        kafkaStream.sendMessage(msg);
    }
}
