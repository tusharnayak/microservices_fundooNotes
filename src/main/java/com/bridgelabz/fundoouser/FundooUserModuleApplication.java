package com.bridgelabz.fundoouser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@SpringBootApplication
@EnableCaching
public class FundooUserModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooUserModuleApplication.class, args);

	}

}
