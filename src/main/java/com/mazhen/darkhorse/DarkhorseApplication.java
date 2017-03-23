package com.mazhen.darkhorse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class DarkhorseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DarkhorseApplication.class, args);
	}
}
