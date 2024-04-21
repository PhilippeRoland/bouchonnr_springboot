package com.philippe.bouchonnr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //used for Listing createDate autofill
@SpringBootApplication
public class BouchonnrApplication {

	public static void main(String[] args) {
		SpringApplication.run(BouchonnrApplication.class, args);
	}

}
