package com.fellow.every.mircoblog;

public class MicroBlogURL {
	
	public enum URLType {
		PICTURE, MUSIC, VIDEO, URL
	}
	
	private String url;

	private URLType type;
	
	public MicroBlogURL(String url, URLType type){
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
