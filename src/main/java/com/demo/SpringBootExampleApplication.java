package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExampleApplication{

	private static Logger logger = LogManager.getLogger(SpringBootExampleApplication.class);

	public static void main(String[] args) {
		logger.info("Demo Logging in SpringBoot Class>>>"+args);
		SpringApplication.run(SpringBootExampleApplication.class, args);
	}
}