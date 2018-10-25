package com.DisastersDemo.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ResourceConfiguration {

	private String accessTokenUri = "https://accounts.spotify.com/api/token";

	/*
	 * @Value("${userAuthorizationUri}") private String userAuthorizationUri;
	 */

	@Value("${spotify.client_id")
	private String spotifyId;

	@Value("${spotify.client_secret")
	private String spotifySecret;

	private final String client_credentials = spotifyId + ":" + spotifySecret;

	@Bean
    public OAuth2ProtectedResourceDetails spotify() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        //details.setId("reddit");
        details.setClientId(spotifyId);
        details.setClientSecret(spotifySecret);
        details.setAccessTokenUri(accessTokenUri);
        details.setGrantType(client_credentials);
        //details.setUserAuthorizationUri(userAuthorizationUri);
        //details.setTokenName("oauth_token");
        //details.setScope(Arrays.asList("identity"));
        //details.setPreEstablishedRedirectUri("http://localhost:8080");
        //details.setUseCurrentUri(false);
        return details;
    }

	@Bean
	public OAuth2RestTemplate spotifyRestTemplate(OAuth2ClientContext clientContext) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(spotify(), clientContext);
		AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(Arrays.<AccessTokenProvider>asList(
				// new MyAuthorizationCodeAccessTokenProvider(),
				new ImplicitAccessTokenProvider(), new ResourceOwnerPasswordAccessTokenProvider(),
				new ClientCredentialsAccessTokenProvider()));
		template.setAccessTokenProvider(accessTokenProvider);
		return template;
	}

}