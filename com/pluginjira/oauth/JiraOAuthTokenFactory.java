package com.pluginjira.oauth;


import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import com.google.api.client.auth.oauth.OAuthRsaSigner;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.jalios.jcms.Channel;
import com.jalios.util.LangProperties;

//intialiser access token and temporary token
// create privateKey
// Signer privatekey

public class JiraOAuthTokenFactory {
	
	 protected final String accessTokenUrl;
	 protected final String requestTokenUrl;
	 private Channel channel = Channel.getChannel();
     private LangProperties properties=channel.getProperties("jcmsplugin.Jiraplugin");

	 //Constructeur
	  public JiraOAuthTokenFactory(String jiraBaseUrl) {
	     this.accessTokenUrl = jiraBaseUrl + "/plugins/servlet/oauth/access-token";
	        requestTokenUrl = jiraBaseUrl + "/plugins/servlet/oauth/request-token";
	    }

	  
	    /*
	     * Create PrivateKey from string
	     */
	  
	    /* @param privateKey private key in PKCS8 format
	     * @return private key
	     * @throws NoSuchAlgorithmException
	     * @throws InvalidKeySpecException
	     */
	  public PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
	        byte[] privateBytes = Base64.decodeBase64(privateKey);
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
	        KeyFactory kf = KeyFactory.getInstance("RSA");
	        return kf.generatePrivate(keySpec);
	    }
	  
	  
	  
	  /* Signature RSA
	     * @param privateKey private key in PKCS8 format
	     * @return OAuthRsaSigner
	     * @throws NoSuchAlgorithmException
	     * @throws InvalidKeySpecException
	     */
	  public OAuthRsaSigner getOAuthRsaSigner(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		    OAuthRsaSigner oAuthRsaSigner = new OAuthRsaSigner();
	        oAuthRsaSigner.privateKey = getPrivateKey(privateKey);
	        return oAuthRsaSigner;
	    }
	    
	    /*
	     * Initialize JiraOAuthGetAccessToken
	     * by setting it to use POST method, secret, request token , consumer and private keys.
	     *
	     * @param tmpToken    request token
	     * @param secret      secret (verification code provided by JIRA after request token authorization)
	     * @param consumerKey consumer key
	     * @param privateKey  private key in PKCS8 format
	     * @return JiraOAuthGetAccessToken request
	     * @throws NoSuchAlgorithmException
	     * @throws InvalidKeySpecException
	     */
	    public JiraOAuthGetAccessToken getJiraOAuthGetAccessToken(String tmpToken, String secret, String consumerKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
	        JiraOAuthGetAccessToken accessToken = new JiraOAuthGetAccessToken(accessTokenUrl);
	        accessToken.consumerKey = consumerKey;
	        accessToken.signer = getOAuthRsaSigner(privateKey);	
	        accessToken.transport = new ApacheHttpTransport();
	        accessToken.verifier = secret;
	        accessToken.temporaryToken = tmpToken;
	        return accessToken;
	    }
	    
	    
	    

	    /*
	     * Initialize JiraOAuthGetTemporaryToken
	     * by setting it to use POST method, oob (Out of Band) callback , consumer and private keys.
	     *
	     * @param consumerKey consumer key
	     * @param privateKey  private key in PKCS8 format
	     * @return JiraOAuthGetTemporaryToken request
	     * @throws NoSuchAlgorithmException
	     * @throws InvalidKeySpecException
	     */
	    
	    public JiraOAuthGetTemporaryToken getTemporaryToken(String consumerKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    	System.out.println("methode getTemporaryToken");
	    	JiraOAuthGetTemporaryToken oAuthGetTemporaryToken = new JiraOAuthGetTemporaryToken(requestTokenUrl);
	    	oAuthGetTemporaryToken.consumerKey = consumerKey;
	        oAuthGetTemporaryToken.signer = getOAuthRsaSigner(privateKey);
	        oAuthGetTemporaryToken.transport = new ApacheHttpTransport();
	        oAuthGetTemporaryToken.callback = "http://localhost:8080/jcms/plugins/Jiraplugin/jsp/layouts/access.jsp";
	        System.out.println("URl token .... "+oAuthGetTemporaryToken.toURL());
	        System.out.println(oAuthGetTemporaryToken.callback.toString());
	        return oAuthGetTemporaryToken;
	    }

}
