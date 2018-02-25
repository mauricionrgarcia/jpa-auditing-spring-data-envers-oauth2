package com.example.conf.token;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import com.example.model.User;

/**
 * Decorator to extract Authentication from map information decoded from an
 * access token
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 21/02/2018 22:53:43
 */
public class UserAuthenticationConverterDecorator extends DefaultUserAuthenticationConverter {
	/**
	 * key add in {@link CustomTokenEnhacer}
	 */
	private static final String KEY_ID_USER = "id";

	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) super.extractAuthentication(
				map);
		if (map.containsKey(KEY_ID_USER)) {
			User user = extractUser(map, auth);
			return new UsernamePasswordAuthenticationToken(user, "N/A", auth.getAuthorities());
		}
		return auth;
	}

	/**
	 * @param map
	 * @param auth
	 * @return
	 */
	private User extractUser(Map<String, ?> map, UsernamePasswordAuthenticationToken auth) {
		User user = new User();
		user.setCode(((Number) map.get(KEY_ID_USER)).longValue());
		user.setEmail(String.valueOf(auth.getPrincipal()));
		return user;
	}

}
