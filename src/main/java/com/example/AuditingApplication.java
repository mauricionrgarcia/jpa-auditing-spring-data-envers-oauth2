package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Example to integrate Auditing in Spring Data JPA with Hibernate envers and
 * oAuth2
 * 
 * @author <a href="mailto:mauricionrgarcia">Mauricio Garcia</a>
 * @version
 * @sinse 21/02/2018 23:13:24
 */
@SpringBootApplication
public class AuditingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditingApplication.class, args);
	}

}
