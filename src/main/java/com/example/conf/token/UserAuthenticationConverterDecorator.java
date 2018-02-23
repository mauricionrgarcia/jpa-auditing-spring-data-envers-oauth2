package com.example.conf.token;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import com.example.model.User;
import com.example.security.dto.SystemUserDTO;

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
	private static final String KEY_ID_USER = "Id";

	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) super.extractAuthentication(
				map);
		if (map.containsKey(KEY_ID_USER)) {
			SystemUserDTO systemUserDTO = extractUser(map, auth);
			return new UsernamePasswordAuthenticationToken(systemUserDTO, "N/A", auth.getAuthorities());
		}
		return auth;
	}

	/**
	 * @param map
	 * @param auth
	 * @return
	 */
	private SystemUserDTO extractUser(Map<String, ?> map, UsernamePasswordAuthenticationToken auth) {
		User user = new User();
		user.setCode((Long) map.get(KEY_ID_USER));
		SystemUserDTO systemUserDTO = new SystemUserDTO(user, auth.getAuthorities());
		return systemUserDTO;
	}

}
