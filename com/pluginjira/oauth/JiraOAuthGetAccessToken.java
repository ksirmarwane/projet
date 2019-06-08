package com.pluginjira.oauth;

import com.google.api.client.auth.oauth.OAuthGetAccessToken;

public class JiraOAuthGetAccessToken extends OAuthGetAccessToken{

	public JiraOAuthGetAccessToken(String authorizationServerUrl) {
		super(authorizationServerUrl);
		this.usePost=true;
	}

	
	
}
