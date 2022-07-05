package com.training.basicrabbitmq.senderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SenderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenderServiceApplication.class, args);
	}

}
