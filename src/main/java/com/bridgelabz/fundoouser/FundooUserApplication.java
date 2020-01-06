package com.bridgelabz.fundoouser;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FundooUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(FundooUserApplication.class, args);
	}

}
