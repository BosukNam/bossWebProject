package com.boss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//		(exclude = RepositoryRestMvcAutoConfiguration.class)
public class BossWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BossWebApplication.class, args);
	}

}
