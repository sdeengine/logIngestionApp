package net.demo.logIngestionApp.service;

import net.demo.logIngestionApp.model.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogBufferService {

    private final List<Log> buffer = new ArrayList<>();

    private final int MAX_BUFFER_SIZE = 1 *  1024 ;

    private MinioService minioService;

    public LogBufferService(MinioService minioService) {
        this.minioService = minioService;
    }

    public synchronized void addLog(Log log) throws Exception {
        buffer.add(log);
        if (estimateBufferSize() >= MAX_BUFFER_SIZE) {
            flushBuffer();
        }
    }

    private int estimateBufferSize() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (Log log : buffer) {
            String line = "{\"timestamp\":" + log.getTimestamp() + ",\"data\":{\"event_name\":\"" + log.getEvent_name() + "\"}}\n";
            baos.write(line.getBytes(StandardCharsets.UTF_8), 0, line.length());
        }
        return baos.size();
    }

    @Scheduled(fixedRate = 10000)
    public synchronized void flushBuffer() throws Exception {
        if (buffer.isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        for (Log log : buffer) {
            sb.append("{\"timestamp\":")
                    .append(log.getTimestamp())
                    .append(",\"data\":{\"event_name\":\"")
                    .append(log.getEvent_name())
                    .append("\"}}\n");
        }
        String batch = sb.toString();

        minioService.uploadLogFile(batch);
        buffer.clear();
    }
}
