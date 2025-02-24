package net.demo.logIngestionApp.service;

import net.demo.logIngestionApp.model.Log;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "logs";

    private KafkaTemplate<String, Log> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Log> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLog(Log log) {
        kafkaTemplate.send(TOPIC, log);
    }
}
