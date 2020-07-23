package com.online.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.online.travel")
public class MyOtaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyOtaApplication.class, args);
	}
}
