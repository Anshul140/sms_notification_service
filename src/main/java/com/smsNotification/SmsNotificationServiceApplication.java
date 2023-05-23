package com.smsNotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class SmsNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsNotificationServiceApplication.class, args);
	}
}

/*@EnableMongoRepositories(basePackages= {"com.smsNotification.repo","com.smsNotification.service.providers","com.smsNotification.service.implementations"})*/