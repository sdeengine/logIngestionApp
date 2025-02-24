package net.demo.logIngestionApp.model;

public class Log {
    private String event_name;
    private long timestamp;

    public Log() {}

    public Log(String event_name, long timestamp) {
        this.event_name = event_name;
        this.timestamp = timestamp;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
