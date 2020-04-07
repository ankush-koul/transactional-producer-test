package com.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuickTest {

    private final String responseTopic;
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    public QuickTest(@Value("${response.topic}") String responseTopic
            , KafkaTemplate<Integer, String> kafkaTemplate) {
        this.responseTopic = responseTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${request.topic}", clientIdPrefix = "MyConsumer")
    public void listen(String requestList) {
        // partition 4 doesnt exist (results in a TimeoutException)
        kafkaTemplate.send(responseTopic, 4, 123456789, null);
    }
}
