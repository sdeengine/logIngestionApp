package net.demo.logIngestionApp.consumer;

import net.demo.logIngestionApp.model.Log;
import net.demo.logIngestionApp.service.LogBufferService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class LogConsumer {

    private LogBufferService logBufferService;

    public LogConsumer(LogBufferService logBufferService) {
        this.logBufferService = logBufferService;
    }

    @KafkaListener(topics = "logs", groupId = "log-group")
    public void consume(Log log) throws Exception {
        logBufferService.addLog(log);
    }
}
