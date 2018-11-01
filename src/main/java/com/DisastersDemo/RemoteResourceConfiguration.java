package com.DisastersDemo;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Configuration
@EnableOAuth2Client
public class RemoteResourceConfiguration {

	@Value("${spotify.client_id")
	private static String spotifyId;

	@Value("${spotify.client_secret")
	private static String spotifySecret;

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
			.setClientId(spotifyId)
			.setClientSecret(spotifySecret)
			.build();
	
	private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
			.build();
	
	public static void clientCredentials_Sync() {
	    try {
	      final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

	      // Set access token for further "spotifyApi" object usage
	      spotifyApi.setAccessToken(clientCredentials.getAccessToken());

	      System.out.println("Expires in: " + clientCredentials.getExpiresIn());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }

	  public static void clientCredentials_Async() {
	    try {
	      final Future<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

	      // ...

	      final ClientCredentials clientCredentials = clientCredentialsFuture.get();

	      // Set access token for further "spotifyApi" object usage
	      spotifyApi.setAccessToken(clientCredentials.getAccessToken());

	      System.out.println("Expires in: " + clientCredentials.getExpiresIn());
	    } catch (InterruptedException | ExecutionException e) {
	      System.out.println("Error: " + e.getCause().getMessage());
	    }
	  }
	
	public static String client_credentials = spotifyId + ":" + spotifySecret;

	public static final String getClientCredentials() {
		byte[] base64EncodedCredentialsAsBytes = Base64.getEncoder().encode(client_credentials.getBytes());
		client_credentials = new String(base64EncodedCredentialsAsBytes);
		return client_credentials;
	}

	public static OAuth2ProtectedResourceDetails spotify() {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		details.setClientId(spotifyId);
		details.setClientSecret(spotifySecret);
		details.setAccessTokenUri("https://accounts.spotify.com/api/token");
		details.setGrantType("client_credentials");
		details.setTokenName("bearer");
		return details;
	}

	@Bean
	public static OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
		return new OAuth2RestTemplate(spotify(), oauth2ClientContext);
	}
	
	@Bean
	public OAuth2RestTemplate spotifyRestTemplate(OAuth2ClientContext clientContext) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(spotify(), clientContext);
        AccessTokenProvider accessTokenProvider = new ClientCredentialsAccessTokenProvider();
        template.setAccessTokenProvider(accessTokenProvider);
        return template;
	}

}
