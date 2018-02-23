package com.example.conf.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 * AuditorAware<br>
 * An audited entity with @CreatedBy, @LastModifiedBy capture the user who
 * created or modified the entity<br>
 * <br>
 * This component implement to tell the infrastructure who current user or
 * system interacting with the application.
 * 
 * The generic type T defines of what type the properties annotated
 * with @CreatedBy or @LastModifiedBy have to be.<br>
 * 
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 21/02/2018 22:03:49
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
	 */
	@Override
	public Long getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		auth.getPrincipal();
		return 1L;
	}

}