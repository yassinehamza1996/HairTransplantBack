package com.hairtransplant.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class HairtransplantApplication {

	public static void main(String[] args) {
		SpringApplication.run(HairtransplantApplication.class, args);
		/* System.setProperty("server.servlet.context-path", "/hair"); */
	}

}
