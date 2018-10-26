package com.DisastersDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableOAuth2Client
@RestController
public class DisastersDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisastersDemoApplication.class, args);
	}
}
