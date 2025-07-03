package com.example.one_on_one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OneOnOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneOnOneApplication.class, args);
	}
}