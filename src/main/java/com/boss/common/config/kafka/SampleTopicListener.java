package com.boss.common.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SampleTopicListener {

    @KafkaListener(topics = "SAMPLE_TOPIC", groupId = "SAMPLE_GROUP")
    public void receive(String payload) {
        log.info("received payload='{}'", payload);
    }
}
