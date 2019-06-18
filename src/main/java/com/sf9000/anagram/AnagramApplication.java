package com.sf9000.anagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AnagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnagramApplication.class, args);
	}

}
