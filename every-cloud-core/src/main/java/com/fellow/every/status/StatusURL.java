package com.fellow.every.status;

public class StatusURL {
	
	public enum URLType {
		PICTURE, MUSIC, VIDEO, URL
	}
	
	private String url;

	private URLType type;
	
	public StatusURL(String url, URLType type){
		this.url = url;
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public URLType getType() {
		return type;
	}
}
