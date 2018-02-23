package com.example.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.dto.SystemUserDTO;

/**
 * Inplmementation of {@link UserDetailsService}
 *
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 10/12/2017 17:32:31
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * userRepository
	 */
	@Autowired
	private transient UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optional = userRepository.findByEmail(email);
		User user = optional.orElseThrow(() -> new UsernameNotFoundException("username.not.found"));
		UserDetails securityUser = new SystemUserDTO(user, getAuthorities(user));
		return securityUser;
	}

	/**
	 * rules
	 *
	 * @param user
	 * @return GrantedAuthority
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getPermissions()
				.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescription().toUpperCase())));
		return authorities;
	}

}
