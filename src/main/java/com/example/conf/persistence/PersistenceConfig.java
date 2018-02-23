package com.example.conf.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.conf.audit.SpringSecurityAuditorAware;

/**
 * Persistence setting enabling jpa audit and overwriting the reference
 * implementation auditorAwareRef<br>
 * 
 * @see SpringSecurityAuditorAware
 * 
 * @author <a href="mauricionrgarcia@gmail.com:"Mauricio Garcia></a>
 * @version
 * @sinse 21/02/2018 22:06:56
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class PersistenceConfig {
}