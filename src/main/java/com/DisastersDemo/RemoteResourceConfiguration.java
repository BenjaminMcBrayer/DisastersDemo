package com.DisastersDemo;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class RemoteResourceConfiguration {

	@Value("${spotify.client_id")
	private static String spotifyId;

	@Value("${spotify.client_secret")
	private static String spotifySecret;

	public static String client_credentials = spotifyId + ":" + spotifySecret;

	public static final String getClientCredentials() {
		byte[] base64EncodedCredentialsAsBytes = Base64.getEncoder().encode(client_credentials.getBytes());
		client_credentials = new String(base64EncodedCredentialsAsBytes);
		return client_credentials;
	}

	public static OAuth2ProtectedResourceDetails spotify() {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		details.setId("reddit");
		details.setClientId(spotifyId);
		details.setClientSecret(spotifySecret);
		details.setAccessTokenUri("https://accounts.spotify.com/api/token");
		details.setGrantType("client_credentials");
		details.setTokenName("oauth_token");
		details.setPreEstablishedRedirectUri("http://localhost:8080");
		details.setUseCurrentUri(false);
		return details;
	}

	@Bean
	public static OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
		return new OAuth2RestTemplate(spotify(), oauth2ClientContext);
	}

}
