package com.fellow.every.http;

import java.net.URLEncoder;
import java.util.*;

public class HTTPRequest {
	private final static String ENCODE = "UTF-8";
	private final static char JOIN_QUESTION = '?';
	private final static char JOIN_AND = '&';
	private final static char JOIN_EQUAL = '=';
	
	private String url;
	private Map<String, String> headers;
	private Map<String, String> queryParameters;
	private Map<String, String> bodyParameters;
	
	public HTTPRequest(String url){
		this.url = url;
		this.headers = new HashMap<String, String>();
		this.queryParameters = new HashMap<String, String>();
		this.bodyParameters = new HashMap<String, String>();
	}
	
	public String getUrl(){
		String p = encodeParameters(queryParameters);
		if(p == null || p.length() == 0){
			return url;
		} else if(url.indexOf(JOIN_QUESTION) >= 0){
			return url + JOIN_AND + p;
		} else {
			return url + JOIN_QUESTION + p;
		}
	}
	
	
	public void addHeader(String name, String value){
		headers.put(name, value);
	}
	
	public void removeHeader(String name){
		headers.remove(name);
	}
	
	public void addQueryParameters(String name, String value){
		queryParameters.put(name, value);
	}
	
	public void removeQueryParameters(String name){
		queryParameters.remove(name);
	}
	
	public void addBodyParameters(String name, String value){
		bodyParameters.put(name, value);
	}
	
	public void removeBodyParameters(String name){
		bodyParameters.remove(name);
	}
	
	public Map<String, String> getHeaders(){
		return Collections.unmodifiableMap(headers);
	}
	
	public Map<String, String> getQueryParameters(){
		return Collections.unmodifiableMap(queryParameters);
	}
	
	public Map<String, String> getBodyParameters(){
		return Collections.unmodifiableMap(bodyParameters);
	}
	

	public static String encodeParameters(Map<String, String> params) {
		StringBuffer buf = new StringBuffer();
		for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			buf.append(key);
			buf.append(JOIN_EQUAL);
			try {
				buf.append(URLEncoder.encode(params.get(key), ENCODE));
			} catch (Exception e) {
				// never come here
			}
			if (iter.hasNext())
				buf.append(JOIN_AND);			
		}
		return buf.toString();
	}
}
