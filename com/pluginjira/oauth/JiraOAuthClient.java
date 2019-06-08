package com.pluginjira.oauth;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.jalios.jcms.Channel;
import com.jalios.util.LangProperties;



public class JiraOAuthClient {
	
	public final String jiraBaseUrl;
	public final JiraOAuthTokenFactory oAuthGetAccessTokenFactory;
	public final String authorizationUrl;
	private Channel channel = Channel.getChannel();
	private LangProperties properties=channel.getProperties("jcmsplugin.Jiraplugin");
	
    public JiraOAuthClient() throws Exception {
    	LangProperties properties=channel.getProperties("jcmsplugin.Jiraplugin");
        this.jiraBaseUrl = properties.get("jcmsplugin.Jiraplugin.jira_home");
        this.oAuthGetAccessTokenFactory = new JiraOAuthTokenFactory(this.jiraBaseUrl);
        this.authorizationUrl = jiraBaseUrl + "/plugins/servlet/oauth/authorize";
    }

    
      /*
     * Creates OAuthParameters used to make authorized request to JIRA
     *
     * @param tmpToken
     * @param secret
     * @param consumerKey
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public OAuthParameters getParameters(String tmpToken, String secret, String consumerKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        
    	JiraOAuthGetAccessToken oAuthAccessToken = oAuthGetAccessTokenFactory.getJiraOAuthGetAccessToken(tmpToken, secret, consumerKey, privateKey);
    	
    	oAuthAccessToken.verifier = secret;
        return oAuthAccessToken.createParameters();
    }
    
    
    
    
    
    
    /*
     * Gets temporary request token and creates url to authorize it
     *
     * @param consumerKey consumer key
     * @param privateKey  private key in PKCS8 format
     * @return request token value
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws IOException
     */
    public String getAndAuthorizeTemporaryToken(String consumerKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
    	
    	JiraOAuthGetTemporaryToken temporaryToken = oAuthGetAccessTokenFactory.getTemporaryToken(consumerKey,privateKey);
    	System.out.println(temporaryToken.toString());
        OAuthCredentialsResponse response = temporaryToken.execute();
        
        System.out.println("Token:\t\t\t" + response.token);
        System.out.println("Token secret:\t" + response.tokenSecret);

        OAuthAuthorizeTemporaryTokenUrl authorizationURL = new OAuthAuthorizeTemporaryTokenUrl(authorizationUrl);
        authorizationURL.temporaryToken = response.token;
        System.out.println("Retrieve request token. Go to " + authorizationURL.toString() + " to authorize it.");
        properties.setProperty("jcmsplugin.Jiraplugin.authorizationURL", authorizationURL.toString());
        channel.updateAndSaveProperties(properties);
        return response.token;
    }

    /*
     * Gets acces token from JIRA
     *
     * @param tmpToken    temporary request token
     * @param secret      secret (verification code provided by JIRA after request token authorization)
     * @param consumerKey consumer ey
     * @param privateKey  private key in PKCS8 format
     * @return access token value
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws IOException
     */
    public String getAccessToken(String tmpToken, String secret, String consumerKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        System.out.println("methode getAccesstoken+JiraOAuthClient");
    	JiraOAuthGetAccessToken oAuthAccessToken = oAuthGetAccessTokenFactory.getJiraOAuthGetAccessToken(tmpToken, secret, consumerKey, privateKey);
        OAuthCredentialsResponse response = oAuthAccessToken.execute();

        System.out.println("Access token:\t\t\t" + response.token);
        return response.token;
    }
    
    
    
    
}
