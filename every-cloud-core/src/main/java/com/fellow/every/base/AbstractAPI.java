package com.fellow.every.base;

import java.util.Properties;

import com.fellow.every.http.HTTPEngine;

public abstract class AbstractAPI {
	private Properties properties;
	private HTTPEngine httpEngine;
	
	private String appKey;
	private String appSecret;

	public AbstractAPI(){
		this.properties = new Properties();
	}
	
	public void setProperties(Properties properties) {
		this.properties.putAll(properties);
	}

	protected Properties getProperties() {
		return properties;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public HTTPEngine getHttpEngine() {
		return httpEngine;
	}

	public void setHttpEngine(HTTPEngine httpEngine) {
		this.httpEngine = httpEngine;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
}
