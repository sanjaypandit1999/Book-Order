package com.bridgelabz.bookorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookOrderApplication.class, args);
	}

}
