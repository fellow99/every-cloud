package com.fellow.every.http.impl;

import java.io.InputStream;

import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponse;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.HTTPStreamUtil;

public class StringHTTPResponseHandler implements HTTPResponseHandler{
	private String info;
	private String charset;
	
	public StringHTTPResponseHandler(){
		
	}
	public StringHTTPResponseHandler(String charset){
		this.charset = charset;
	}
	public String getString(){
		return info;
	}
	public void handle(HTTPRequest request, HTTPResponse response, InputStream is) throws ServerException, ApiException{
		if(response.getCode() == 200){
			if(charset == null || charset.length() == 0){
				info = HTTPStreamUtil.toString(is);
			} else {
				info = HTTPStreamUtil.toString(is, charset);
			}
		} else {
			String url = request.getUrl();
			String code = new Integer(response.getCode()).toString();
			String message = response.getMessage();
			info = HTTPStreamUtil.toString(is);
			throw new ServerException(url, code, message, info);
		}
	}
}