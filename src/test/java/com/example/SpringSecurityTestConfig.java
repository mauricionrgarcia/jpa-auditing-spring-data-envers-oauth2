package com.example;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.security.dto.SystemUserDTO;

/**
 * Add UserDetailsService to mock user detail<br>
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 25/02/2018 15:05:52
 */
@TestConfiguration()
@Component("SpringSecurityTestConfig")
public class SpringSecurityTestConfig implements UserDetailsService {

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		user.setCode(1L);
		user.setEmail("admin");
		user.setPassword("admin");

		List<SimpleGrantedAuthority> auths = Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"));

		return new SystemUserDTO(user, auths);

	}

}