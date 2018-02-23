package com.example.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.example.model.User;

/**
 * System user
 *
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 20/12/2017 23:14:43
 */
public class SystemUserDTO extends org.springframework.security.core.userdetails.User {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3344351231190088311L;

	/**
	 * User entity
	 */
	private User user;

	/**
	 * Constructor
	 * 
	 * @param user {@link User}
	 * @param authorities {@link GrantedAuthority}
	 */
	public SystemUserDTO(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

}
