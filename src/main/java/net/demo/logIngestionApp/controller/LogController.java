package net.demo.logIngestionApp.controller;

import net.demo.logIngestionApp.model.Log;
import net.demo.logIngestionApp.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private KafkaProducerService kafkaProducerService;

    public LogController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/log")
    public ResponseEntity<String> ingestLog(@RequestBody Log log) {
        log.setTimestamp(System.currentTimeMillis() / 1000L);
        kafkaProducerService.sendLog(log);
        return ResponseEntity.ok("Log received");
    }
}