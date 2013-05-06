package com.fellow.every.provider.qq;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.TokenExtractor20Impl;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.utils.OAuthEncoder;

public class QQOAuth2API extends DefaultApi20 {
	//http://developer.baidu.com/
	private static final String AUTHORIZE_URL = "https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code";
	private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";

	@Override
	public Verb getAccessTokenVerb() {
		return Verb.GET;
	}

	@Override
	public AccessTokenExtractor getAccessTokenExtractor() {
		return new TokenExtractor20Impl();
	}

	@Override
	public String getAccessTokenEndpoint() {
		return "https://open.t.qq.com/cgi-bin/oauth2/access_token?grant_type=authorization_code";
	}

	@Override
	public String getAuthorizationUrl(OAuthConfig config) {
		// Append scope if present
		if (config.hasScope()) {
			return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(),
					OAuthEncoder.encode(config.getCallback()),
					OAuthEncoder.encode(config.getScope()));
		} else {
			return String.format(AUTHORIZE_URL, config.getApiKey(),
					OAuthEncoder.encode(config.getCallback()));
		}
	}
}