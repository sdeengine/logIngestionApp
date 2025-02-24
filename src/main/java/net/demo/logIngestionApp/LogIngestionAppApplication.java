package net.demo.logIngestionApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogIngestionAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogIngestionAppApplication.class, args);
	}

}
