package com.pluginjira.oauth;


import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;

public class JiraOAuthGetTemporaryToken extends OAuthGetTemporaryToken {

	public JiraOAuthGetTemporaryToken(String authorizationServerUrl) {
		super(authorizationServerUrl);
		this.usePost=true;
	}

}
