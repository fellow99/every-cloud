package com.fellow.every.http.impl;

import java.io.InputStream;

import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponse;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.HTTPStreamUtil;

public class SimpleHTTPResponseHandler implements HTTPResponseHandler{
	public void handle(HTTPRequest request, HTTPResponse response, InputStream is) throws ServerException, ApiException{
		if(response.getCode() == 200){
			//Do nothing
		} else {
			String url = request.getUrl();
			String code = new Integer(response.getCode()).toString();
			String message = response.getMessage();
			String info = HTTPStreamUtil.toString(is);
			throw new ServerException(url, code, message, info);
		}
	}
}