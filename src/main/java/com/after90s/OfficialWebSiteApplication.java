package com.after90s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OfficialWebSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficialWebSiteApplication.class, args);
	}

}
