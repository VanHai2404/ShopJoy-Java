package com.edu.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.edu.shop.config.StorageProperties;
import com.edu.shop.service.StorageService;

import groovyjarjarantlr4.v4.runtime.misc.Args;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ShopJoyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopJoyApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return(args ->{
			storageService.init();
			
		});
		
	}

}
