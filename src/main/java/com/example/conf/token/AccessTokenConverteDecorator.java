package com.example.conf.token;

import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import com.example.conf.security.AuthorizationServerConfig;

/**
 * Decorator to extract custom values from jwt token.<br>
 * In the Authorization Server Config was adding user information in
 * OAuth2AccessToken.<br>
 * 
 * @see CustomTokenEnhacer
 * @see AuthorizationServerConfig
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio Garcia</a>
 * @version
 * @sinse 21/02/2018 22:36:58
 */
public class AccessTokenConverteDecorator extends DefaultAccessTokenConverter {

	/**
	 * UserAuthenticationConverterDecorator
	 */
	private final UserAuthenticationConverter userTokenConverter = new UserAuthenticationConverterDecorator();

	/**
	 * contructor no args
	 */
	public AccessTokenConverteDecorator() {
		super();
		super.setUserTokenConverter(userTokenConverter);
	}

	@Override
	public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
		return super.extractAccessToken(value, map);
	}

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		return super.extractAuthentication(map);
	}

}
