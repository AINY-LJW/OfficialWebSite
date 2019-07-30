package com.after90s;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.after90s.project.*.*.mapper")
//com.after90s.project.web.homePage.mapper
public class OfficialWebSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficialWebSiteApplication.class, args);
	}

}
